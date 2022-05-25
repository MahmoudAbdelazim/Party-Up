package com.partyup.model;

import javax.persistence.*;

@Entity
@IdClass(StateId.class)
public class State {
    @Id
    private String name;

    @Id
    @ManyToOne
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
