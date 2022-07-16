package com.partyup.controller;

import com.partyup.assembler.PostModelAssembler;
import com.partyup.model.Player;
import com.partyup.model.posting.ContentData;
import com.partyup.model.posting.Post;
import com.partyup.payload.PostModel;
import com.partyup.payload.PostUploadDto;
import com.partyup.payload.ProfileToken;
import com.partyup.payload.UploadResponse;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.PostContentService;
import com.partyup.service.PostingService;
import com.partyup.service.exception.PostNotFoundException;
import com.partyup.service.exception.UploadFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostingController {
	@Autowired
	private PostingService postingService;
	@Autowired
	private PostContentService postContentService;
	@Autowired
	private PlayerRepository playerRepo;

	@Autowired
	private PagedResourcesAssembler<Post> pagedModelAssembler;

	@Autowired
	private PostModelAssembler postModelAssembler;

	@PostMapping("/upload")
	public ResponseEntity<UploadResponse> postPost(@ModelAttribute PostUploadDto post, UriComponentsBuilder uriComponentsBuilder)
			throws UploadFailedException, UsernameNotFoundException {

		String username = getUserName();
		Optional<Player> player = playerRepo.findByUsernameOrEmail(username, username);
		if (player.isEmpty()) {
			throw new UsernameNotFoundException("Username is not authorized");
		}
		UriComponents currentURI = ServletUriComponentsBuilder.fromCurrentRequest().build();
		uriComponentsBuilder.scheme(currentURI.getScheme()).host(currentURI.getHost()).path("/api/file");
		List<ContentData> contents = postContentService.saveContents(
				post.getFiles(),
				uriComponentsBuilder
		);
		post.setContents(contents);
		post.clearFiles();
		postingService.savePostOfUser(post, player.get());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new UploadResponse("Post uploaded successfully"));
	}

	@GetMapping("/{id}")
	public PostModel getPostOf(@PathVariable("id") String id) throws PostNotFoundException {
		Post post = postingService.getPostOfId(id);
		ProfileToken owner = new ProfileToken(post.getPlayer().getUsername(), post.getPlayer().getProfilePicture());
		PostModel postModel = new PostModel(post, owner);
		postModel.add(linkTo(methodOn(PostingController.class).getPostOf(id)).withSelfRel());
		return postModel;
	}

	@GetMapping("/profile")
	public PagedModel<PostModel> getOwnPosts(@PageableDefault(page = 0, size = 10) Pageable page) throws UsernameNotFoundException {
		String username = getUserName();
		Optional<Player> player = playerRepo.findByUsernameOrEmail(username, username);
		if (player.isEmpty()) throw new UsernameNotFoundException("Username doesn't exist");

		Page<Post> posts = postingService.getPostsOfUser(player.get(), page);
		return pagedModelAssembler.toModel(posts, postModelAssembler);
	}

	@GetMapping("/feed")
	public PagedModel<PostModel> getFeed(@PageableDefault(page = 0, size = 10) Pageable page) throws UsernameNotFoundException {
		String username = getUserName();
		Optional<Player> player = playerRepo.findByUsernameOrEmail(username, username);
		if (player.isEmpty()) throw new UsernameNotFoundException("Username doesn't exist");

		Page<Post> posts = postingService.getPostsRelatedToUser(player.get(), page);
		return pagedModelAssembler.toModel(posts, postModelAssembler);
	}

	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<String> sendFileNotFound(PostNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<UsernameNotFoundException> sendForbidden(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
	}

	@ExceptionHandler(UploadFailedException.class)
	public ResponseEntity<UsernameNotFoundException> sendUploadFailed(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e);
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
