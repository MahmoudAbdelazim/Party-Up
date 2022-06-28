package com.partyup.controller;

import com.partyup.model.Player;
import com.partyup.payload.PostDto;
import com.partyup.payload.UploadResponse;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.PostingService;
import com.partyup.service.exception.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/post")
public class PostingController {

	@Autowired
	private PostingService postingService;

	@Autowired
	private PlayerRepository playerRepo;

	@PostMapping("/upload")
	public ResponseEntity<UploadResponse> postPost(@RequestBody PostDto post) {
		String username = getUserName();
		Optional<Player> player = playerRepo.findByUsernameOrEmail(username, username);
		if (player.isEmpty()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
		postingService.savePostOfUser(post, player.get());
		return ResponseEntity.status(HttpStatus.OK)
				.body(new UploadResponse("Post uploaded successfully"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostOf(@PathVariable("id") Long id) throws PostNotFoundException {
		PostDto post = postingService.getPostOfId(id);
		return ResponseEntity.ok().body(post);
	}

	@GetMapping("/profile")
	public ResponseEntity<List<PostDto>> getPosts() throws UsernameNotFoundException {
		String username = getUserName();
		Optional<Player> player = playerRepo.findByUsernameOrEmail(username, username);
		if (player.isEmpty()) throw new UsernameNotFoundException("Username doesn't exist");
		List<PostDto> posts = postingService.getPostsOfUser(player.get());
		return ResponseEntity.ok().body(posts);
	}

	@GetMapping("/feed")
	public ResponseEntity<List<PostDto>> getFeed() throws UsernameNotFoundException {
		String username = getUserName();
		Optional<Player> player = playerRepo.findByUsernameOrEmail(username, username);
		if (player.isEmpty()) throw new UsernameNotFoundException("Username doesn't exist");
		List<PostDto> posts = postingService.getPostsRelatedToUser(player.get());
		return ResponseEntity.ok().body(posts);
	}

	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<PostNotFoundException> sendFileNotFound(PostNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<UsernameNotFoundException> sendFileNotFound(UsernameNotFoundException e) {
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
