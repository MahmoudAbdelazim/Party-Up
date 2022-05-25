package com.partyup.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Country {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    private String continent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String content) {
        this.continent = content;
    }
}
