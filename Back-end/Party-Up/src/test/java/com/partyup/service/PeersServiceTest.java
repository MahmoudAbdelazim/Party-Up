package com.partyup.service;

import com.partyup.model.Game;
import com.partyup.model.Player;
import com.partyup.model.Question;
import com.partyup.model.Rate;
import com.partyup.payload.AnswerDto;
import com.partyup.payload.ProfileToken;
import com.partyup.payload.QuestionDto;
import com.partyup.repository.PeerRequestRepository;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.QuestionsRepository;
import com.partyup.service.exception.*;
import com.sun.mail.iap.ByteArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PeersServiceTest {
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PeerRequestRepository peerRequestRepository;
    @Mock
    private QuestionsRepository questionsRepository;
    @Mock
    private GameService gameService;
    @Mock
    private AuthService authService;
    @Mock
    private MLModelService mlModelService;
    @InjectMocks
    private PeersService peersService;

    Player player1;
    Player player2;

    @BeforeEach
    void init() throws UserNotAuthenticatedException {
        MockitoAnnotations.openMocks(this);
        player1 = new Player();
        player1.setFirstName("First1");
        player1.setLastName("Last1");
        player1.setDiscordTag("Disc#1234");
        player1.setPassword("1234");
        player1.setEmail("player1@example.com");
        player1.setUsername("player1");
        player1.setPeers(new HashSet<>());
        player2 = new Player();
        player2.setFirstName("First2");
        player2.setLastName("Last2");
        player2.setDiscordTag("Disc#2134");
        player2.setPassword("1234");
        player2.setEmail("player2@example.com");
        player2.setUsername("player2");
        player2.setPeers(new HashSet<>());
        when(authService.authenticate()).thenReturn(player1);
    }

    @Test
    void addPeer() throws UserNotAuthenticatedException, PlayerNotFoundException {
        when(playerRepository.save(any())).thenReturn(null);
        when(playerRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(player2));
        assertEquals(peersService.addPeer("player2"), "Peer Request is sent successfully");
    }

    @Test
    void addPeerNonExistingUsername() {
        when(playerRepository.save(any())).thenReturn(null);
        when(playerRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        assertThrows(PlayerNotFoundException.class, () -> peersService.addPeer("player2"));
    }

    @Test
    void unpeer() throws UserNotAuthenticatedException, PlayerNotFoundException {
        player1.addPeer(player2);
        player2.addPeer(player1);
        when(playerRepository.save(any())).thenReturn(null);
        when(playerRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(player2));
        assertEquals(peersService.unpeer("player2"), "Unpeered Successfully");
    }

    @Test
    void unpeerNotPeer() throws UserNotAuthenticatedException, PlayerNotFoundException {
        when(playerRepository.save(any())).thenReturn(null);
        Player player = new Player();
        player.setUsername("player3");
        when(playerRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(player));
        assertEquals(peersService.unpeer("player3"), "User is not a peer");
    }

    @Test
    void findPeers() throws GameNotFoundException, UserNotAuthenticatedException {
        Game game = new Game();
        game.setName("Apex");
        when(gameService.getGameBy(any())).thenReturn(game);
        ProfileToken profileToken1 = new ProfileToken();
        profileToken1.setUsername("p1");
        ProfileToken profileToken2 = new ProfileToken();
        profileToken2.setUsername("p2");
        List<ProfileToken> profileTokens = new ArrayList<>();
        profileTokens.add(profileToken1);
        profileTokens.add(profileToken2);
        when(mlModelService.getSuggestedProfileTokens(any(), any())).thenReturn(profileTokens);
        List<ProfileToken> response = peersService.findPeers("Apex");
        assertEquals(profileToken1, response.get(0));
        assertEquals(profileToken2, response.get(1));
    }

    @Test
    void myPeers() throws UserNotAuthenticatedException {
        player1.addPeer(player2);
        player2.addPeer(player1);
        List<ProfileToken> response = peersService.myPeers();
        assertEquals(response.get(0).getUsername(), player2.getUsername());
        assertEquals(response.size(), 1);
    }

    @Test
    void getPeerReviewQuestions() {
        List<Question> questions = new ArrayList<>();
        Question q1 = new Question();
        q1.setReviewQuestionString("q1");
        Question q2 = new Question();
        q2.setReviewQuestionString("q2");
        Question q3 = new Question();
        q3.setReviewQuestionString("q3");
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        when(questionsRepository.findAll()).thenReturn(questions);
        List<QuestionDto> response = peersService.getPeerReviewQuestions();
        int found = 0;
        for (QuestionDto questionDto : response) {
            for (Question question : questions) {
                if (question.getReviewQuestionString().equals(questionDto.getQuestion())) {
                    found++;
                    break;
                }
            }
        }
        assertEquals(response.size(), 3);
        assertEquals(found, 3);
    }

    @Test
    void reviewPeer() throws UserNotAuthenticatedException, PlayerNotFoundException,
            PlayerIsNotPeerException, PlayerAlreadyReviewedException {
        when(playerRepository.save(any())).thenReturn(null);
        when(playerRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(player2));
        player1.addPeer(player2);
        player2.addPeer(player1);
        Rate rate1 = new Rate();
        rate1.setRate(2);
        rate1.setQuestionID(1L);
        Rate rate2 = new Rate();
        rate2.setRate(3);
        rate2.setQuestionID(2L);
        List<Rate> rates = new ArrayList<>();
        rates.add(rate1);
        rates.add(rate2);
        player2.setRates(rates);
        player2.setReviewers(new ArrayList<>());

        List<AnswerDto> answerDtos = new ArrayList<>();
        AnswerDto answerDto1 = new AnswerDto();
        answerDto1.setAnswer(5);
        answerDto1.setId(1L);
        AnswerDto answerDto2 = new AnswerDto();
        answerDto2.setAnswer(1);
        answerDto2.setId(2L);
        answerDtos.add(answerDto1);
        answerDtos.add(answerDto2);
        assertEquals(peersService.reviewPeer("player2", answerDtos), "Player is reviewed successfully");
    }

    @Test
    void reviewPeerNotPeer() {
        when(playerRepository.save(any())).thenReturn(null);
        when(playerRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(player2));
        List<AnswerDto> answerDtos = new ArrayList<>();
        AnswerDto answerDto1 = new AnswerDto();
        answerDto1.setAnswer(5);
        answerDto1.setId(1L);
        AnswerDto answerDto2 = new AnswerDto();
        answerDto2.setAnswer(1);
        answerDto2.setId(2L);
        answerDtos.add(answerDto1);
        answerDtos.add(answerDto2);
        assertThrows(PlayerIsNotPeerException.class, () -> peersService.reviewPeer("player2", answerDtos));
    }

    @Test
    void reviewPeerAlreadyReviewed() {
        when(playerRepository.save(any())).thenReturn(null);
        when(playerRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(player2));
        player1.addPeer(player2);
        player2.addPeer(player1);
        player2.addReviewer(player1);
        List<AnswerDto> answerDtos = new ArrayList<>();
        AnswerDto answerDto1 = new AnswerDto();
        answerDto1.setAnswer(5);
        answerDto1.setId(1L);
        AnswerDto answerDto2 = new AnswerDto();
        answerDto2.setAnswer(1);
        answerDto2.setId(2L);
        answerDtos.add(answerDto1);
        answerDtos.add(answerDto2);
        assertThrows(PlayerAlreadyReviewedException.class, () -> peersService.reviewPeer("player2", answerDtos));
    }
}