package com.partyup.service;

import com.partyup.model.FollowRequest;
import com.partyup.model.Player;
import com.partyup.repository.FollowRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
class FollowServiceTest {

	static private Player playerOne;
	static private Player playerTwo;
	static private FollowRequest followRequest;

	@Mock
	FollowRepository followRepository;

	@InjectMocks
	FollowService followService;

	@BeforeAll
	static void intitialize() {
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

		followRequest = new FollowRequest(1L, 2L);
		followRequest.setId("1");
	}

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		List<FollowRequest> followRequests = new ArrayList<>(List.of(followRequest));
		when(followRepository.save(any(FollowRequest.class))).thenReturn(followRequest);
		when(followRepository.findAllByFollower(1L)).thenReturn(followRequests);
		when(followRepository.findAllByFollower(2L)).thenReturn(new ArrayList<FollowRequest>());
		when(followRepository.countFollowRequestByFollower(any())).thenReturn(1L);
		when(followRepository.countFollowRequestByFollowee(any())).thenReturn(1L);
	}

	@Test
	void followFromTo() {
		FollowRequest request = followService.followFromTo(playerOne, playerTwo);
		assertEquals(request, followRequest);
	}

	@Test
	void findAllByFollowerId() {
		List<FollowRequest> followers = followService.findAllByFollowerId(1L);
		assertEquals(followers, new ArrayList<>(List.of(followRequest)));
	}

	@Test
	void findAllByFollowerIdEmpty() {
		List<FollowRequest> followers = followService.findAllByFollowerId(2L);
		assertEquals(followers, new ArrayList<>());
	}
}