package com.partyup.controller;

import com.partyup.payload.ProfileDto;
import com.partyup.service.PlayerProfileService;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class PlayerProfileController {

    PlayerProfileService playerProfileService;

    @Autowired
    public PlayerProfileController(PlayerProfileService playerProfileService) {
        this.playerProfileService = playerProfileService;
    }

    @GetMapping
    public ProfileDto getPlayerProfile() throws UserNotAuthenticatedException, PlayerNotFoundException {
        return playerProfileService.getPlayerProfile();
    }
}
