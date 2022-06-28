package com.partyup.service.exception;

public class PostNotFoundException extends Exception {
	Long id;

	public PostNotFoundException(Long id) {
		this.id = id;
	}

	@Override
	public String getMessage() {
		return "Post of id: " + id + " not found";
	}
}
