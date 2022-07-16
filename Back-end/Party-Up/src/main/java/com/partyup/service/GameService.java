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
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private GameRepository gameRepository;

    private PlayerRepository playerRepository;

    private HandleRepository handleRepository;

    private AuthService authService;

    @Autowired
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository,
                       HandleRepository handleRepository, AuthService authService) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.handleRepository = handleRepository;
        this.authService = authService;
    }

    public String addGame(AddGameDto addGameDto)
            throws UserNotAuthenticatedException, GameNotFoundException {
        Player player = authService.authenticate();
        Optional<Game> game = gameRepository.findByName(addGameDto.getGameName());
        if (game.isEmpty())
            throw new GameNotFoundException("Game with Name: " + addGameDto.getGameName() + " is Not Found");
        Optional<Handle> existingHandle = handleRepository.findByHandleNameAndGame(addGameDto.getHandle(), game.get());
        if (existingHandle.isPresent())
            return "Handle is already present for this game";
        Handle handle = new Handle();
        handle.setHandleName(addGameDto.getHandle());
        handle.setGame(game.get());
        player.addHandle(handle);
        playerRepository.save(player);
        return "Game Added Successfully";
    }

    public Game getGameBy(String name) throws GameNotFoundException {
        Optional<Game> game = gameRepository.findByName(name);
        if (game.isEmpty()) {
            throw new GameNotFoundException("Game with Name: " + name + " is Not Found");
        }
        return game.get();
    }

    public List<GameDto> getAllGames() {
        List<Game> games = gameRepository.findAll();
        List<GameDto> gameDtos = new ArrayList<>();
        for (Game game : games) {
            gameDtos.add(new GameDto(game.getName()));
        }
        return gameDtos;
    }

    public List<GameDto> myGames() throws UserNotAuthenticatedException {
        Player player = authService.authenticate();
        List<GameDto> gameDtos = new ArrayList<>();
        for (Handle handle : player.getHandles()) {
            boolean found = false;
            for (GameDto gameDto : gameDtos) {
                if (gameDto.getName().equalsIgnoreCase(handle.getGame().getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                GameDto gameDto = new GameDto();
                gameDto.setName(handle.getGame().getName());
                gameDtos.add(gameDto);
            }
        }
        return gameDtos;
    }
}
