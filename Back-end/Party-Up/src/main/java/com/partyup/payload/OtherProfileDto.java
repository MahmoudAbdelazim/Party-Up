package com.partyup.payload;

import com.partyup.model.Country;
import com.partyup.model.Handle;
import com.partyup.model.Player;

import java.util.ArrayList;
import java.util.List;

public class OtherProfileDto {

    String username;
    List<HandleDto> handles;
    Country country;

    boolean isPeer = false;

    boolean requested = false;

    boolean otherRequested = false;

    public OtherProfileDto(Player player) {
        username = player.getUsername();
        country = player.getCountry();
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

    public String getUsername() {
        return username;
    }

    public Country getCountry() {
        return country;
    }

    public boolean isPeer() {
        return isPeer;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
