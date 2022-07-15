package com.partyup.model;

import com.partyup.model.posting.ContentData;

import javax.persistence.*;

@Entity
@Table(name = "peer_request")
public class PeerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    private ContentData profilePicture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ContentData getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ContentData profilePicture) {
        this.profilePicture = profilePicture;
    }
}