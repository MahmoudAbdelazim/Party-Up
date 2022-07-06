package com.partyup.controller;

import com.partyup.payload.AddGameDto;
import com.partyup.service.GameService;
import com.partyup.service.exception.GameNotFoundException;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/api/addGame")
    public ResponseEntity<String> addGame(@RequestBody AddGameDto addGameDto)
            throws UserNotAuthenticatedException, PlayerNotFoundException, GameNotFoundException {
        return ResponseEntity.ok(gameService.addGame(addGameDto));
    }
}
