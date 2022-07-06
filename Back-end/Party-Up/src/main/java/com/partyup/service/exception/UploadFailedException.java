package com.partyup.service.exception;

public class UploadFailedException extends Exception {
	private final String message;

	public UploadFailedException() {
		message = "Couldn't upload files";
	}

	public UploadFailedException(String customMessage) {
		this.message = customMessage;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
