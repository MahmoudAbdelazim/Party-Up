package com.partyup.service;

import com.partyup.model.*;
import com.partyup.payload.AnswerDto;
import com.partyup.payload.ProfileToken;
import com.partyup.payload.QuestionDto;
import com.partyup.payload.SuggestedPeerId;
import com.partyup.repository.PeerRequestRepository;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.QuestionsRepository;
import com.partyup.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class PeersService {
    private final PlayerRepository playerRepository;
    private final PeerRequestRepository peerRequestRepository;
    private final QuestionsRepository questionsRepository;
    private final GameService gameService;
    private final AuthService authService;

    private final MLModelService mlModelService;

    @Autowired
    public PeersService(PlayerRepository playerRepository,
                        PeerRequestRepository peerRequestRepository,
                        QuestionsRepository questionsRepository,
                        GameService gameService,
                        AuthService authService,
                        MLModelService mlModelService) {
        this.playerRepository = playerRepository;
        this.peerRequestRepository = peerRequestRepository;
        this.gameService = gameService;
        this.authService = authService;
        this.questionsRepository = questionsRepository;
        this.mlModelService = mlModelService;
    }

    public List<ProfileToken> getRequests() throws UserNotAuthenticatedException {
        Player player = authService.authenticate();
        List<ProfileToken> profileTokens = new ArrayList<>();
        for (PeerRequest peerRequest : player.getPeerRequests()) {
            ProfileToken profileToken = new ProfileToken();
            profileToken.setUsername(peerRequest.getUsername());
            Player otherPlayer = playerRepository.findByUsernameOrEmail(peerRequest.getUsername(), peerRequest.getUsername()).get();
            profileToken.setProfilePicture(otherPlayer.getProfilePicture());
            profileTokens.add(profileToken);
        }
        return profileTokens;
    }

    public String addPeer(String playerUsername)
            throws UserNotAuthenticatedException, PlayerNotFoundException {
        Player player = authService.authenticate();
        Player otherPlayer = getOtherPlayer(playerUsername);
        PeerRequest peerRequest = new PeerRequest();
        peerRequest.setUsername(player.getUsername());
        peerRequest.setProfilePicture(player.getProfilePicture());
        otherPlayer.addPeerRequest(peerRequest);
        playerRepository.save(otherPlayer);
        return "Peer Request is sent successfully";
    }

    public String respondPeerRequest(String playerUsername, String response)
            throws UserNotAuthenticatedException, PlayerNotFoundException, PeerRequestNotFoundException {
        Player player = authService.authenticate();
        Player otherPlayer = getOtherPlayer(playerUsername);

        PeerRequest peerRequest = null;
        for (PeerRequest pr : player.getPeerRequests()) {
            if (pr.getUsername().equals(playerUsername)) {
                peerRequest = pr;
            }
        }
        if (peerRequest == null) {
            throw new PeerRequestNotFoundException();
        }
        if (response.equalsIgnoreCase("accept")) {
            player.addPeer(otherPlayer);
            otherPlayer.addPeer(player);
        }
        player.getPeerRequests().remove(peerRequest);
        playerRepository.save(player);
        playerRepository.save(otherPlayer);
        peerRequestRepository.delete(peerRequest);
        return "Peer Responded Successfully";
    }

    public String unpeer(String playerUsername)
            throws UserNotAuthenticatedException, PlayerNotFoundException {
        Player player = authService.authenticate();
        Player otherPlayer = getOtherPlayer(playerUsername);

        if (player.hasPeer(otherPlayer)) {
            player.getPeers().remove(otherPlayer);
            otherPlayer.getPeers().remove(player);
            playerRepository.save(player);
            playerRepository.save(otherPlayer);
            return "Unpeered Successfully";
        }
        return "User is not a peer";
    }

    public List<ProfileToken> findPeers(String gameName)
            throws UserNotAuthenticatedException, GameNotFoundException {
        Player player = authService.authenticate();
        Game game = gameService.getGameBy(gameName);
        return mlModelService.getSuggestedProfileTokens(player, game);
    }

    public List<ProfileToken> myPeers()
            throws UserNotAuthenticatedException {
        Player player = authService.authenticate();
        List<ProfileToken> profileTokens = new ArrayList<>();
        for (Player peer : player.getPeers()) {
            ProfileToken profileToken = new ProfileToken();
            profileToken.setUsername(peer.getUsername());
            profileToken.setProfilePicture(peer.getProfilePicture());
            profileTokens.add(profileToken);
        }
        return profileTokens;
    }

    public List<QuestionDto> getPeerReviewQuestions() {
        List<Question> questions = questionsRepository.findAll();
        Collections.shuffle(questions);
        questions = questions.subList(0, Math.min(10, questions.size()));
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question: questions) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setQuestion(question.getReviewQuestionString());
            questionDto.setId(question.getId());
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }

    public String reviewPeer(String playerUsername, List<AnswerDto> answers)
            throws UserNotAuthenticatedException, PlayerNotFoundException, PlayerAlreadyReviewedException, PlayerIsNotPeerException {
        Player player = authService.authenticate();
        Player otherPlayer = getOtherPlayer(playerUsername);
        if (!player.hasPeer(otherPlayer)) {
            throw new PlayerIsNotPeerException("Player with username: " + playerUsername + " is not a peer");
        }
        if (otherPlayer.getReviewers().contains(player)) {
            throw new PlayerAlreadyReviewedException("Player with username: " + playerUsername + " has already been reviewed before");
        }
        answers.sort((AnswerDto a1, AnswerDto a2) -> (int) (a1.getId() - a2.getId()));
        int numReviewers = otherPlayer.getReviewers().size();
        int j = 0;
        AnswerDto answerDto = answers.get(j);
        for (int i = 0; i < otherPlayer.getRates().size(); i++) {
            Rate rate = otherPlayer.getRates().get(i);
            if (Objects.equals(rate.getQuestionID(), answerDto.getId())) {
                rate.setRate((rate.getRate() * numReviewers + answerDto.getAnswer()) / (numReviewers + 1));
                j++;
                if (j == answers.size()) break;
                answerDto = answers.get(j);
            }
        }
        otherPlayer.addReviewer(player);
        playerRepository.save(otherPlayer);
        return "Player is reviewed successfully";
    }

    private Player getOtherPlayer(String playerUsername) throws PlayerNotFoundException {
        Optional<Player> otherPlayer = playerRepository.findByUsernameOrEmail(playerUsername, playerUsername);
        if (otherPlayer.isEmpty()) {
            throw new PlayerNotFoundException(playerUsername);
        }
        return otherPlayer.get();
    }
}
