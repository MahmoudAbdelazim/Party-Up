package com.partyup.service.exception;

public class GameNotFoundException extends Exception{
    private Long id;

    public GameNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Game with ID: " + id + " Not Found";
    }
}
