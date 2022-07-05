package com.partyup.service;

import com.partyup.model.Player;
import com.partyup.payload.HandleDto;
import com.partyup.payload.ProfileDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PlayerProfileService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerProfileService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public ProfileDto getPlayerProfile() throws PlayerNotFoundException, UserNotAuthenticatedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            String username = getUsername(auth);
            Optional<Player> p = playerRepository.findByUsernameOrEmail(username, username);
            if (p.isPresent()) {
                Player player = p.get();
                ProfileDto profileDto = new ProfileDto();
                profileDto.setUsername(player.getUsername());
                profileDto.setEmail(player.getEmail());
                profileDto.setFirstName(player.getFirstName());
                profileDto.setLastName(player.getLastName());
                profileDto.setPhoneNumber(player.getPhoneNumber());
                //
                profileDto.setHandles(new ArrayList<>());
                HandleDto handle = new HandleDto();
                handle.setGameId(1L);
                handle.setGame("League Of Legends");
                handle.setId(1L);
                handle.setHandle("LOLLL");
                profileDto.getHandles().add(handle);
                return profileDto;
            } else {
                throw new PlayerNotFoundException(username);
            }
        } else {
            throw new UserNotAuthenticatedException();
        }
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
