package com.partyup.service;

import com.partyup.model.Handle;
import com.partyup.model.PeerRequest;
import com.partyup.model.Player;
import com.partyup.payload.HandleDto;
import com.partyup.payload.OtherProfileDto;
import com.partyup.payload.ProfileDto;
import com.partyup.payload.SignUpDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerProfileService {

    private final PlayerRepository playerRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PlayerProfileService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
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
                profileDto.setDiscordTag(player.getDiscordTag());
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

    public String editProfile(SignUpDto signUpDto) throws UserNotAuthenticatedException {
        Player player = authenticate();
        if (playerRepository.findAllByEmail(signUpDto.getEmail()).size() > 1) {
            return "Email is already taken!";
        }
        if (playerRepository.findAllByUsername(signUpDto.getUsername()).size() > 1) {
            return "Username is already taken!";
        }
        if (playerRepository.findAllByDiscordTag(signUpDto.getDiscordTag()).size() > 1) {
            return "Discord tag is already taken!";
        }
        player.setUsername(signUpDto.getUsername());
        player.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        player.setEmail(signUpDto.getEmail());
        player.setCountry(signUpDto.getCountry());
        player.setDiscordTag(signUpDto.getDiscordTag());
        player.setFirstName(signUpDto.getFirstName());
        player.setLastName(signUpDto.getLastName());
        playerRepository.saveAndFlush(player);
        return "Profile Edited Successfully";
    }

    public OtherProfileDto getOtherPlayerProfile(String playerUsername)
            throws PlayerNotFoundException, UserNotAuthenticatedException {

        Optional<Player> optionalPlayer = playerRepository.findByUsernameOrEmail(playerUsername, playerUsername);
        if (optionalPlayer.isEmpty()) {
            throw new PlayerNotFoundException(playerUsername);
        }
        Player otherPlayer = optionalPlayer.get();
        OtherProfileDto profileDto = new OtherProfileDto(otherPlayer);
        Player player = authenticate();
        if (player.hasPeer(otherPlayer)) {
            profileDto.setPeer(true);
            profileDto.setHandles(otherPlayer.getHandles());
            profileDto.setDiscordTag(otherPlayer.getDiscordTag());
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
        return profileDto;
    }

    private Player authenticate() throws UserNotAuthenticatedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) throw new UserNotAuthenticatedException();
        String username = getUsername(auth);
        return playerRepository.findByUsernameOrEmail(username, username).get();
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
