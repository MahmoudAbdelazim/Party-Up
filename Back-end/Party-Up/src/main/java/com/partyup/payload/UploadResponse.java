package com.partyup.payload;

public class UploadResponse {
	private final String message;
	public UploadResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
