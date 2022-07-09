package com.partyup.controller;

import com.partyup.model.Player;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/follow")
public class FollowingController {


	@Autowired
	PlayerRepository playerRepo;

	@Autowired
	FollowService followService;

	@PostMapping("/{followeeUsername}")
	public ResponseEntity<String> follow(@PathVariable String followeeUsername) {
		String followerUsername = getUserName();
		Optional<Player> follower = playerRepo.findByUsernameOrEmail(followerUsername, followerUsername);
		if (follower.isEmpty()) {
			throw new UsernameNotFoundException("User is not authorized");
		}
		Optional<Player> followee = playerRepo.findByUsernameOrEmail(followeeUsername, followeeUsername);
		if (followee.isEmpty()) {
			throw new UsernameNotFoundException("Followee doesn't exist");
		}
		followService.followFromTo(follower.get(), followee.get());
		return ResponseEntity.status(HttpStatus.CREATED).body("Followed");
	}


	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<UsernameNotFoundException> sendForbidden(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
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
