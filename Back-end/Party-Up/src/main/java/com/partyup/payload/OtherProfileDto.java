package com.partyup.payload;

import com.partyup.model.Country;
import com.partyup.model.Handle;
import com.partyup.model.Player;

import java.util.ArrayList;
import java.util.List;

public class OtherProfileDto extends ProfileDto {
    List<HandleDto> handles;

    String firstName;

    String lastName;
    Country country;
    String discordTag;
    boolean isPeer = false;
    boolean requested = false;
    boolean otherRequested = false;

    boolean reviewed = false;

    public OtherProfileDto(Player player) {
        username = player.getUsername();
        country = player.getCountry();
        profilePicture = player.getProfilePicture();
        handles = new ArrayList<>();
    }

    public List<HandleDto> getHandles() {
        return handles;
    }

    public void setHandles(List<Handle> handles) {
        isPeer = true;
        requested = false;
        otherRequested = false;
        for (Handle handle : handles) {
            this.handles.add(new HandleDto(handle));
        }
    }

    public Country getCountry() {
        return country;
    }

    public boolean isPeer() {
        return isPeer;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setPeer(boolean peer) {
        isPeer = peer;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public boolean isOtherRequested() {
        return otherRequested;
    }

    public void setOtherRequested(boolean otherRequested) {
        this.otherRequested = otherRequested;
    }

    public String getDiscordTag() {
        return discordTag;
    }

    public void setDiscordTag(String discordTag) {
        this.discordTag = discordTag;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
