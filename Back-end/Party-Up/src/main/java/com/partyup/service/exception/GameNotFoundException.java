package com.partyup.service.exception;

public class GameNotFoundException extends Exception{
    private final String message;

    public GameNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
