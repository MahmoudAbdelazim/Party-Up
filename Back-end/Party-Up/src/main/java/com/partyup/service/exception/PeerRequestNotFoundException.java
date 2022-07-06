package com.partyup.service.exception;

public class PeerRequestNotFoundException extends Exception{
    @Override
    public String getMessage() {
        return "Peer Request is not found";
    }
}
