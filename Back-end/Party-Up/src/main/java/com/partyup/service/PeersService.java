package com.partyup.service;

import com.partyup.model.Game;
import com.partyup.model.PeerRequest;
import com.partyup.model.Player;
import com.partyup.payload.ProfileToken;
import com.partyup.repository.PeerRequestRepository;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.exception.GameNotFoundException;
import com.partyup.service.exception.PeerRequestNotFoundException;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeersService {

    private final PlayerRepository playerRepository;

    private final PeerRequestRepository peerRequestRepository;

    private final GameService gameService;

    @Autowired
    public PeersService(PlayerRepository playerRepository, PeerRequestRepository peerRequestRepository, GameService gameService) {
        this.playerRepository = playerRepository;
        this.peerRequestRepository = peerRequestRepository;
        this.gameService = gameService;
    }

    public List<ProfileToken> getRequests() throws UserNotAuthenticatedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        List<ProfileToken> profileTokens = new ArrayList<>();
        for (PeerRequest peerRequest : player.getPeerRequests()) {
            profileTokens.add(new ProfileToken(peerRequest));
        }
        return profileTokens;
    }

    public String addPeer(String playerUsername) throws UserNotAuthenticatedException, PlayerNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        Optional<Player> otherPlayer = playerRepository.findByUsernameOrEmail(playerUsername, playerUsername);

        if (otherPlayer.isEmpty()) {
            throw new PlayerNotFoundException(playerUsername);
        }
        PeerRequest peerRequest = new PeerRequest();
        peerRequest.setUsername(player.getUsername());
        otherPlayer.get().addPeerRequest(peerRequest);
        playerRepository.save(otherPlayer.get());
        return "Peer Request is sent successfully";
    }

    public String respondPeerRequest(String playerUsername, String response)
            throws UserNotAuthenticatedException, PlayerNotFoundException, PeerRequestNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        Optional<Player> otherPlayer = playerRepository.findByUsernameOrEmail(playerUsername, playerUsername);

        if (otherPlayer.isEmpty()) {
            throw new PlayerNotFoundException(playerUsername);
        }

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
            player.addPeer(otherPlayer.get());
            otherPlayer.get().addPeer(player);
        }
        player.getPeerRequests().remove(peerRequest);
        playerRepository.save(player);
        playerRepository.save(otherPlayer.get());
        peerRequestRepository.delete(peerRequest);
        return "Peer Responded Successfully";
    }

    public String unpeer(String playerUsername)
            throws UserNotAuthenticatedException, PlayerNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        Optional<Player> otherPlayer = playerRepository.findByUsernameOrEmail(playerUsername, playerUsername);

        if (otherPlayer.isEmpty()) {
            throw new PlayerNotFoundException(playerUsername);
        }

        if (player.hasPeer(otherPlayer.get())) {
            player.getPeers().remove(otherPlayer.get());
            otherPlayer.get().getPeers().remove(player);
            playerRepository.save(player);
            playerRepository.save(otherPlayer.get());
            return "Unpeered Successfully";
        }
        return "User is not a peer";
    }

    public List<ProfileToken> findPeers(String gameName)
            throws UserNotAuthenticatedException, GameNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        RestTemplate restTemplate = new RestTemplate();
        Game game = gameService.getGameBy(gameName);
        String findPeersUri = UriComponentsBuilder.fromHttpUrl("http://localhost:5000")
                .path("/" + player.getId())
                .path("/" + game.getId())
                .encode().toUriString();
        List<SuggestedPeerId> suggestedPeerIds =  restTemplate.getForEntity(findPeersUri, List.class).getBody();
        return getProfileTokensOf(suggestedPeerIds);
    }

    private List<ProfileToken> getProfileTokensOf(List<SuggestedPeerId> suggestedPeerIds) {
        List<ProfileToken> profileTokens = new ArrayList<>();
        for (SuggestedPeerId peerId: suggestedPeerIds) {
            ProfileToken profileToken = new ProfileToken();
            Player player = playerRepository.findById(peerId.getId()).get();
            profileToken.setUsername(player.getUsername());
            profileTokens.add(profileToken);
        }
        return profileTokens;
    }

    private String getUsername(Authentication authentication) {
        Object userSessionData = authentication.getPrincipal();
        String username;
        if (userSessionData instanceof UserDetails) {
            username = ((UserDetails) userSessionData).getUsername();
        } else {
            username = userSessionData.toString();
        }
        return username;
    }

    public class SuggestedPeerId {
        private Long id;

        public SuggestedPeerId(Long id) {
            this.id = id;
        }

        public SuggestedPeerId() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
