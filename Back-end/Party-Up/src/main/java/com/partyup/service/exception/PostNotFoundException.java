package com.partyup.service.exception;

public class PostNotFoundException extends Exception {
	private String id;
	private String message;

	public PostNotFoundException(String id) {
		this.id = id;
		message = "Post not found";
	}

	public PostNotFoundException(String id, String customMessage) {
		this.id = id;
		this.message = customMessage;
	}

	@Override
	public String getMessage() {
		return message + " id:" + id;
	}
}
