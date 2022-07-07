package com.partyup.payload;

public class SuggestedPeerId {
    private Long id;

    public SuggestedPeerId(Long id) {
        this.id = id;
    }

    public SuggestedPeerId() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
