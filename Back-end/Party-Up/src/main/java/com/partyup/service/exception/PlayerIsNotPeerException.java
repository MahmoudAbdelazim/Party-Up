package com.partyup.service.exception;

public class PlayerIsNotPeerException extends Exception{

    String message;

    public PlayerIsNotPeerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
