package com.partyup.service;

import com.partyup.model.Handle;
import com.partyup.model.PeerRequest;
import com.partyup.model.Player;
import com.partyup.model.posting.Content;
import com.partyup.model.posting.ContentData;
import com.partyup.payload.HandleDto;
import com.partyup.payload.OtherProfileDto;
import com.partyup.payload.OwnProfileDto;
import com.partyup.payload.SignUpDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.ContentRepository;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UploadFailedException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import com.partyup.util.FileCompressionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
public class PlayerProfileService {

    private final PlayerRepository playerRepository;

    private final PasswordEncoder passwordEncoder;

    private final ContentRepository contentRepo;

    private final AuthService authService;

    @Autowired
    public PlayerProfileService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder,
                                ContentRepository contentRepo, AuthService authService) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.contentRepo = contentRepo;
        this.authService = authService;
    }

    public void updateProfilePic(MultipartFile picture, UriComponentsBuilder uriBuilder)
            throws UploadFailedException {
        try {
            Player player = authService.authenticate();
            var picContent = new Content(
                    picture.getName(),
                    picture.getContentType(),
                    FileCompressionUtility.compressFile(picture.getBytes())
            );
            contentRepo.save(picContent);
            URL fileURL = getURLOf(picContent, uriBuilder);
            ContentData profilePicFileData = new ContentData(picContent.getType(), picContent.getSize(), fileURL);
            player.setProfilePicture(profilePicFileData);
            playerRepository.save(player);
        } catch (IOException | UserNotAuthenticatedException e) {
            throw new UploadFailedException("Couldn't read file");
        }
    }

    public OwnProfileDto getPlayerProfile() throws UserNotAuthenticatedException {
        Player player = authService.authenticate();
        OwnProfileDto ownProfileDto = new OwnProfileDto();
        ownProfileDto.setUsername(player.getUsername());
        ownProfileDto.setEmail(player.getEmail());
        ownProfileDto.setFirstName(player.getFirstName());
        ownProfileDto.setLastName(player.getLastName());
        ownProfileDto.setDiscordTag(player.getDiscordTag());
        ownProfileDto.setProfilePicture(player.getProfilePicture());
        ownProfileDto.setCountry(player.getCountry());
        for (Handle handle : player.getHandles()) {
            ownProfileDto.getHandles().add(new HandleDto(handle));
        }
        return ownProfileDto;
    }

    public String editProfile(SignUpDto signUpDto) throws UserNotAuthenticatedException {
        Player player = authService.authenticate();
        if (playerRepository.findAllByEmail(signUpDto.getEmail()).size() > 1) {
            return "Email is already taken!";
        }
        if (playerRepository.findAllByUsername(signUpDto.getUsername()).size() > 1) {
            return "Username is already taken!";
        }
        if (playerRepository.findAllByDiscordTag(signUpDto.getDiscordTag()).size() > 1) {
            return "Discord Tag is already taken!";
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
        Player player = authService.authenticate();
        if (player.hasPeer(otherPlayer)) {
            profileDto.setPeer(true);
            profileDto.setHandles(otherPlayer.getHandles());
            profileDto.setDiscordTag(otherPlayer.getDiscordTag());
            if (otherPlayer.getReviewers().contains(player)) profileDto.setReviewed(true);
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
        profileDto.setFirstName(otherPlayer.getFirstName());
        profileDto.setLastName(otherPlayer.getLastName());
        return profileDto;
    }

    private URL getURLOf(Content content, UriComponentsBuilder uriBuilder) throws UploadFailedException {
        try {
            var uriClone = (UriComponentsBuilder) uriBuilder.clone();
            String uri = uriClone.path("/" + content.getId()).encode().toUriString();
            return new URL(uri);
        } catch (MalformedURLException e) {
            var specific = new UploadFailedException("Couldn't generate URLs");
            specific.setStackTrace(e.getStackTrace());
            throw specific;
        }
    }
}
