package com.partyup.payload;

import java.util.List;

public class ProfileDto {

    String username;

    String email;

    String firstName;

    String lastName;

    String phoneNumber;

    List<HandleDto> handles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<HandleDto> getHandles() {
        return handles;
    }

    public void setHandles(List<HandleDto> handles) {
        this.handles = handles;
    }
}
