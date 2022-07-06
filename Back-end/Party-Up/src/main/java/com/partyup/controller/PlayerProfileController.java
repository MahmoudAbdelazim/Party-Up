package com.partyup.controller;

import com.partyup.payload.OtherProfileDto;
import com.partyup.payload.ProfileDto;
import com.partyup.service.PlayerProfileService;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class PlayerProfileController {

    PlayerProfileService playerProfileService;

    @Autowired
    public PlayerProfileController(PlayerProfileService playerProfileService) {
        this.playerProfileService = playerProfileService;
    }

    @GetMapping
    public ResponseEntity<ProfileDto> getPlayerProfile()
            throws UserNotAuthenticatedException, PlayerNotFoundException {
        return ResponseEntity.ok().body(playerProfileService.getPlayerProfile());
    }

    @GetMapping("/{username}")
    public OtherProfileDto getOtherPlayerProfile(@PathVariable String username)
            throws PlayerNotFoundException {
        return playerProfileService.getOtherPlayerProfile(username);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> sendPlayerNotFound(PlayerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<String> sendUserNotAuthenticated(UserNotAuthenticatedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
