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
}
