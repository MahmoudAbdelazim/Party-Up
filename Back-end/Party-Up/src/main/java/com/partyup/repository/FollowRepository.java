package com.partyup.repository;

import com.partyup.model.FollowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<FollowRequest, String> {

	List<FollowRequest> findAllByFollower(Long followerId);

	Long countFollowRequestByFollowee(Long followeeId);

	Long countFollowRequestByFollower(Long followerId);
}
