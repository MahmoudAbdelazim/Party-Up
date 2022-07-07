package com.partyup.service;

import com.partyup.model.Handle;
import com.partyup.model.PeerRequest;
import com.partyup.model.Player;
import com.partyup.payload.HandleDto;
import com.partyup.payload.OtherProfileDto;
import com.partyup.payload.ProfileDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
                for (Handle handle : player.getHandles()) {
                    profileDto.getHandles().add(new HandleDto(handle));
                }
                return profileDto;
            } else {
                throw new PlayerNotFoundException(username);
            }
        } else {
            throw new UserNotAuthenticatedException();
        }
    }

    public OtherProfileDto getOtherPlayerProfile(String playerUsername) throws PlayerNotFoundException {

        Optional<Player> optionalPlayer = playerRepository.findByUsernameOrEmail(playerUsername, playerUsername);
        if (optionalPlayer.isEmpty()) {
            throw new PlayerNotFoundException(playerUsername);
        }
        Player otherPlayer = optionalPlayer.get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OtherProfileDto profileDto = new OtherProfileDto(otherPlayer);
        if (auth.isAuthenticated()) {
            String username = getUsername(auth);
            Player player = playerRepository.findByUsernameOrEmail(username, username).get();
            if (player.hasPeer(otherPlayer)) {
                profileDto.setPeer(true);
                profileDto.setHandles(otherPlayer.getHandles());
            } else {
                for (PeerRequest peerRequest : player.getPeerRequests()) {
                    if (peerRequest.getUsername().equals(otherPlayer.getUsername())) {
                        profileDto.setOtherRequested(true);
                        break;
                    }
                }
                for (PeerRequest peerRequest : otherPlayer.getPeerRequests()) {
                    if (peerRequest.getUsername().equals(player.getUsername())) {
                        profileDto.setRequested(true);
                        break;
                    }
                }
            }
        }
        return profileDto;
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
