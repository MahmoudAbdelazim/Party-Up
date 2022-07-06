package com.partyup.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "game", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
