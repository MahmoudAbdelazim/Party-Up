package com.partyup.service;

import com.partyup.model.FollowRequest;
import com.partyup.model.Player;
import com.partyup.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

	@Autowired
	FollowRepository followRepository;

	public void followFromTo(Player follower, Player followee) {
		FollowRequest request = new FollowRequest(follower.getId(), followee.getId());
		followRepository.save(request);
	}
}
