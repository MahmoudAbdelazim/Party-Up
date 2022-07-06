package com.partyup.service;

import com.partyup.model.Game;
import com.partyup.model.Handle;
import com.partyup.model.Player;
import com.partyup.payload.AddGameDto;
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

import java.util.Optional;

@Service
public class GameService {

    private GameRepository gameRepository;

    private PlayerRepository playerRepository;

    private HandleRepository handleRepository;

    @Autowired
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository, HandleRepository handleRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.handleRepository = handleRepository;
    }

    public String addGame(AddGameDto addGameDto)
            throws UserNotAuthenticatedException, PlayerNotFoundException, GameNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            String username = getUsername(auth);
            Optional<Player> player = playerRepository.findByUsernameOrEmail(username, username);
            if (player.isEmpty())
                throw new PlayerNotFoundException(username);
            gameRepository.flush();
            Optional<Game> game = gameRepository.findById(addGameDto.getGameId());
            if (game.isEmpty())
                throw new GameNotFoundException("Game with ID: " + addGameDto.getGameId() + " is Not Found");
            Optional<Handle> existingHandle = handleRepository.findByHandleNameAndGame(addGameDto.getHandle(), game.get());
            if (existingHandle.isPresent())
                return "Handle is already present for this game";
            Handle handle = new Handle();
            handle.setHandleName(addGameDto.getHandle());
            handle.setGame(game.get());
            player.get().addHandle(handle);
            playerRepository.save(player.get());
            return "Game Added Successfully";
        } else {
            throw new UserNotAuthenticatedException();
        }
    }

    public Game getGameBy(String name) throws GameNotFoundException {
        Optional<Game> game = gameRepository.findByName(name);
        if (game.isEmpty()) {
            throw new GameNotFoundException("Game with Name: " + name + " is Not Found");
        }
        return game.get();
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
}
