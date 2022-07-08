package com.partyup.controller;

import com.partyup.payload.ProfileDto;
import com.partyup.payload.SignUpDto;
import com.partyup.service.PlayerProfileService;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UploadFailedException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

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
	public ResponseEntity<ProfileDto> getOtherPlayerProfile(@PathVariable String username)
			throws PlayerNotFoundException, UserNotAuthenticatedException {
		String clientUsername = getUserName();
		if (username.equals(clientUsername)) return getPlayerProfile();
		return ResponseEntity.ok().body(playerProfileService.getOtherPlayerProfile(username));
	}

	@PutMapping()
	public ResponseEntity<String> editProfile(@RequestBody SignUpDto signUpDto) throws UserNotAuthenticatedException {
		return ResponseEntity.ok().body(playerProfileService.editProfile(signUpDto));
	}

	@PutMapping(value = "profilePic")
	public ResponseEntity<String> editProfilePic(MultipartFile picture, UriComponentsBuilder uriComponentsBuilder)
			throws UploadFailedException {
		if (!Objects.requireNonNull(picture.getContentType()).contains("image")) {
			return ResponseEntity.badRequest().body("File type not allowed");
		}
		UriComponents currentURI = ServletUriComponentsBuilder.fromCurrentRequest().build();
		uriComponentsBuilder.scheme(currentURI.getScheme()).host(currentURI.getHost()).path("/api/file");
		playerProfileService.updateProfilePic(picture, uriComponentsBuilder);
		return ResponseEntity.accepted().body("Profile Pic updated");

	}

	@ExceptionHandler(PlayerNotFoundException.class)
	public ResponseEntity<String> sendPlayerNotFound(PlayerNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@ExceptionHandler(UserNotAuthenticatedException.class)
	public ResponseEntity<String> sendUserNotAuthenticated(UserNotAuthenticatedException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}

	@ExceptionHandler(UploadFailedException.class)
	public ResponseEntity<String> sendUploadFailed(UserNotAuthenticatedException exception) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Couldn't upload file");
	}

	private String getUserName() {
		Object userSessionData = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (userSessionData instanceof UserDetails) {
			username = ((UserDetails) userSessionData).getUsername();
		} else {
			username = userSessionData.toString();
		}
		return username;
	}
}
