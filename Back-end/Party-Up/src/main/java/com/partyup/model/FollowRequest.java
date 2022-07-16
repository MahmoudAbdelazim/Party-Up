package com.partyup.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FollowRequest {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	String id;
	Long follower;
	Long followee;

	public FollowRequest() {
		follower = 0L;
		followee = 0L;
	}

	public FollowRequest(Long follower, Long followee) {
		this.follower = follower;
		this.followee = followee;
	}

	public Long getFollower() {
		return follower;
	}

	public Long getFollowee() {
		return followee;
	}

	public void setFollower(Long follower) {
		this.follower = follower;
	}

	public void setFollowee(Long followee) {
		this.followee = followee;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
