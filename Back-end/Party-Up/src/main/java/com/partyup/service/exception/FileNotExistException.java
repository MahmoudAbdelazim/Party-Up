package com.partyup.service.exception;

public class FileNotExistException extends Exception {
	private String message;

	public FileNotExistException() {
		message = "File Doesn't exist";
	}

	public FileNotExistException(String customMessage) {
		this.message = customMessage;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
