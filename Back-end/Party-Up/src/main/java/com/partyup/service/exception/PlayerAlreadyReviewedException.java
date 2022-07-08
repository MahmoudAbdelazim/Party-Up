package com.partyup.service.exception;

public class PlayerAlreadyReviewedException extends Exception{

    String message;

    public PlayerAlreadyReviewedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
