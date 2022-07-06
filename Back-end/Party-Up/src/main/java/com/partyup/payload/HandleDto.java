package com.partyup.payload;

import com.partyup.model.Handle;

public class HandleDto {

    private Long id;
    private String handle;
    private Long gameId;
    private String game;

    public HandleDto(Handle handle) {
        this.id = handle.getId();
        this.handle = handle.getHandleName();
        this.gameId = handle.getGame().getId();
        this.game = handle.getGame().getName();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
