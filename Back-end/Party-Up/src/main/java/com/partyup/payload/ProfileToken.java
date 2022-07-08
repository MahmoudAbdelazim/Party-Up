package com.partyup.payload;

import com.partyup.model.PeerRequest;
import com.partyup.model.posting.ContentData;

public class ProfileToken {

    String username;

    ContentData profilePicture;

    public ProfileToken(String username, ContentData profilePic) {
        this.username = username;
        this.profilePicture = profilePic;
    }

    public ProfileToken() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ContentData getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ContentData profilePicture) {
        this.profilePicture = profilePicture;
    }
}
