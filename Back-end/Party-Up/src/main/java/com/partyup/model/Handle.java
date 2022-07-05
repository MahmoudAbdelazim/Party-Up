package com.partyup.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Handle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Game game;

    private String handleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handle) {
        this.handleName = handle;
    }
}
