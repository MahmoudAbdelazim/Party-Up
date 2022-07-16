package com.partyup.service;

import com.partyup.model.FollowRequest;
import com.partyup.model.Player;
import com.partyup.model.posting.Post;
import com.partyup.payload.PostUploadDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.PostRepository;
import com.partyup.service.exception.PostNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
class PostingServiceTest {
	static private Player playerOne;
	static private Player playerTwo;
	static private Post savedPost;
	static private PostUploadDto postUploadDto;
	@Mock
	PostRepository postRepository;
	@Mock
	FollowService followService;

	@Mock
	PlayerRepository playerRepository;

	@Mock
	ModelMapper postingMapper;
	@InjectMocks
	PostingService postingService;

	@BeforeAll
	static void initialize() {
		playerOne = new Player();
		playerOne.setId(1L);
		playerOne.setEmail("amrbomadian8@gmail.com");
		playerOne.setFirstName("amr");
		playerOne.setLastName("bumadian");
		playerOne.setUsername("amrbumadian");

		playerTwo = new Player();
		playerTwo.setId(1L);
		playerTwo.setEmail("amrbomadian8@gmail.com");
		playerTwo.setFirstName("amr");
		playerTwo.setLastName("bumadian");
		playerTwo.setUsername("amrbumadian");

		postUploadDto = new PostUploadDto();
		postUploadDto.setText("This is a testing post");

		savedPost = new Post();
		savedPost.setId("1");
		savedPost.setText(postUploadDto.getText());
		savedPost.setPlayer(playerOne);
	}

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		FollowRequest followRequest = new FollowRequest(1L,2L);
		followRequest.setId("1");

		List<Post> posts = new ArrayList<>(List.of(savedPost));
		List<Player> otherPlayers = new ArrayList<>(List.of(playerTwo));
		List<FollowRequest> followRequests = new ArrayList<>(List.of(followRequest));
		Page<Post> page = new PageImpl<Post>(posts);

		when(postingMapper.map(any(PostUploadDto.class), any())).thenReturn(savedPost);
		when(postRepository.save(any(Post.class))).thenReturn(savedPost);
		when(postRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(savedPost));
		when(postRepository.findAllByPlayer(any(Player.class))).thenReturn(posts);
		when(postRepository.findAllByPlayerOrderByCreateAt(any(Player.class), any(Pageable.class))).thenReturn(page);
		when(postRepository.findAllByPlayerInOrderByCreateAt(any(List.class), any(Pageable.class))).thenReturn(page);

		when(followService.findAllByFollowerId(any(Long.class))).thenReturn(followRequests);
		when(followService.followFromTo(any(Player.class), any(Player.class))).thenReturn(followRequest);

		when(playerRepository.findAllByIdIn(any(List.class))).thenReturn(otherPlayers);

	}

	@Test
	void savePostOfUser() {
		Post post = postingService.savePostOfUser(postUploadDto, playerOne);
		assertEquals(post.getText(), postUploadDto.getText());
		assertEquals(post.getPlayer(), playerOne);
	}

	@Test
	void getPostOfId() throws PostNotFoundException {
		Post post = postingService.savePostOfUser(postUploadDto, playerOne);
		Post post2 = postingService.getPostOfId(post.getId());
		assertEquals(post, post2);
	}

	@Test
	void getPostOfIdPostNotFound() {
		when(postRepository.findById(any())).thenReturn(Optional.empty());
		assertThrows(PostNotFoundException.class, () -> postingService.getPostOfId("25"));
	}

	@Test
	void getPostsOfUser() {
		Post post = postingService.savePostOfUser(postUploadDto, playerOne);
		Page<Post> page = postingService.getPostsOfUser(playerOne, Pageable.ofSize(1));
		List<Post> posts = page.stream().toList();
		assertEquals(post, posts.get(0));
	}

	@Test
	void getPostsRelatedToUser() {
		Post post = postingService.savePostOfUser(postUploadDto, playerTwo);
		followService.followFromTo(playerOne, playerTwo);
		Page<Post> page = postingService.getPostsRelatedToUser(playerOne, Pageable.ofSize(1));
		List<Post> posts = page.stream().toList();
		assertEquals(post, posts.get(0));
	}
}