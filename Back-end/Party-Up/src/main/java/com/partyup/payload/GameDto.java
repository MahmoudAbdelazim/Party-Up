package com.partyup.payload;

public class GameDto {
    String name;

    public GameDto() {
    }

    public GameDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
