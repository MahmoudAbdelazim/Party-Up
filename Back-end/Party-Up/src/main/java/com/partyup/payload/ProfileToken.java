package com.partyup.payload;

import com.partyup.model.PeerRequest;

public class ProfileToken {

    String username;

    public ProfileToken(String username) {
        this.username = username;
    }

    public ProfileToken(PeerRequest peerRequest) {
        this.username = peerRequest.getUsername();
    }

    public ProfileToken() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
