package com.partyup.service;

import com.partyup.model.Game;
import com.partyup.model.Handle;
import com.partyup.model.Player;
import com.partyup.payload.AddGameDto;
import com.partyup.payload.GameDto;
import com.partyup.repository.GameRepository;
import com.partyup.repository.HandleRepository;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.exception.GameNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private HandleRepository handleRepository;
    @Mock
    private AuthService authService;
    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void init() throws UserNotAuthenticatedException {
        MockitoAnnotations.openMocks(this);
        Player player = new Player();
        player.setFirstName("First");
        player.setLastName("Last");
        player.setDiscordTag("Disc#1234");
        player.setPassword("1234");
        player.setEmail("user@example.com");
        player.setUsername("user");
        when(authService.authenticate()).thenReturn(player);
    }

    @Test
    void addGame() throws UserNotAuthenticatedException, GameNotFoundException {
        when(gameRepository.findByName(anyString())).thenReturn(Optional.of(new Game()));
        when(handleRepository.findByHandleNameAndGame(anyString(), any())).thenReturn(Optional.empty());
        when(playerRepository.save(any())).thenReturn(null);
        AddGameDto gameDto = new AddGameDto();
        gameDto.setGameName("League Of Legends");
        gameDto.setHandle("LOL");
        assertEquals(gameService.addGame(gameDto), "Game Added Successfully");
    }

    @Test
    void addGameWithNonExistingGameName() {
        when(gameRepository.findByName(anyString())).thenReturn(Optional.empty());
        AddGameDto gameDto = new AddGameDto();
        gameDto.setGameName("League Of Legends");
        gameDto.setHandle("LOL");
        assertThrows(GameNotFoundException.class, () -> gameService.addGame(gameDto));
    }

    @Test
    void addGameWithExistingHandle() throws UserNotAuthenticatedException, GameNotFoundException {
        when(gameRepository.findByName(anyString())).thenReturn(Optional.of(new Game()));
        when(handleRepository.findByHandleNameAndGame(anyString(), any())).thenReturn(Optional.of(new Handle()));
        AddGameDto gameDto = new AddGameDto();
        gameDto.setGameName("League Of Legends");
        gameDto.setHandle("LOL");
        assertEquals(gameService.addGame(gameDto), "Handle is already present for this game");
    }

    @Test
    void getAllGames() {
        List<Game> games = new ArrayList<>();
        Game game1 = new Game();
        game1.setName("League Of Legends");
        Game game2 = new Game();
        game2.setName("Apex");
        Game game3 = new Game();
        game3.setName("Call Of Duty");
        games.add(game1);
        games.add(game2);
        games.add(game3);
        when(gameRepository.findAll()).thenReturn(games);
        List<GameDto> response = gameService.getAllGames();
        assertEquals(response.get(0).getName(), "League Of Legends");
        assertEquals(response.get(1).getName(), "Apex");
        assertEquals(response.get(2).getName(), "Call Of Duty");
    }

    @Test
    void myGames() throws UserNotAuthenticatedException {
        Player player = new Player();
        Game game1 = new Game();
        game1.setName("League Of Legends");
        Handle handle1 = new Handle();
        handle1.setGame(game1);
        handle1.setHandleName("Handle1");
        Game game2 = new Game();
        game2.setName("Apex");
        Handle handle2 = new Handle();
        handle2.setGame(game2);
        handle2.setHandleName("Handle2");
        List<Handle> handles = new ArrayList<>();
        handles.add(handle1);
        handles.add(handle2);
        player.setHandles(handles);
        when(authService.authenticate()).thenReturn(player);
        List<GameDto> response = gameService.myGames();
        assertEquals(response.get(0).getName(), "League Of Legends");
        assertEquals(response.get(1).getName(), "Apex");
    }

    @Test
    void myGamesWithTwoHandlesForSameGame() throws UserNotAuthenticatedException {
        Player player = new Player();
        Game game1 = new Game();
        game1.setName("League Of Legends");
        Handle handle1 = new Handle();
        handle1.setGame(game1);
        handle1.setHandleName("Handle1");
        Game game2 = new Game();
        game2.setName("League Of Legends");
        Handle handle2 = new Handle();
        handle2.setGame(game2);
        handle2.setHandleName("Handle2");
        List<Handle> handles = new ArrayList<>();
        handles.add(handle1);
        handles.add(handle2);
        player.setHandles(handles);
        when(authService.authenticate()).thenReturn(player);
        List<GameDto> response = gameService.myGames();
        assertEquals(response.get(0).getName(), "League Of Legends");
        assertEquals(1, response.size());
    }
}