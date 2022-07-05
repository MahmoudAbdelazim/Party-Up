package com.partyup.service.exception;

public class PlayerNotFoundException extends Exception{

    String username;

    public PlayerNotFoundException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "Player with username: " + username + " Not Found";
    }
}
