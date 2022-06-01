package com.partyup.model;

import javax.persistence.*;

@Entity
@IdClass(StateId.class)
public class State {
    @Id
    private String name;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
