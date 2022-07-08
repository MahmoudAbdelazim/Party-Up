package com.partyup.model;

import com.partyup.model.posting.ContentData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "player", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"discord_tag"})
})
public class Player implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @Column(name = "discord_tag")
    private String discordTag;

    @ManyToOne
    private Country country;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Rate> rates;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Handle> handles;

    @ManyToMany
    private Set<Player> peers;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PeerRequest> peerRequests;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    private ContentData profilePicture;

    @ManyToMany
    private List<Player> reviewers;

    public ContentData getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ContentData profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<PeerRequest> getPeerRequests() {
        return peerRequests;
    }

    public void setPeerRequests(List<PeerRequest> peerRequests) {
        this.peerRequests = peerRequests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> userRate) {
        this.rates = userRate;
    }

    public List<Handle> getHandles() {
        return handles;
    }

    public void setHandles(List<Handle> handles) {
        this.handles = handles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        if (roles == null) roles = new HashSet<>();
        roles.add(role);
    }

    public boolean removeRole(Role role) {
        return roles.remove(role);
    }

    public void setRoles(Set<Role> role) {
        this.roles = role;
    }

    public void addHandle(Handle handle) {
        if (handles == null) handles = new ArrayList<>();
        handles.add(handle);
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Player> getPeers() {
        return peers;
    }

    public void setPeers(Set<Player> peers) {
        this.peers = peers;
    }

    public boolean hasPeer(Player player) {
        return peers.contains(player);
    }

    public void addPeerRequest(PeerRequest peerRequest) {
        if (peerRequests == null) peerRequests = new ArrayList<>();
        peerRequests.add(peerRequest);
    }

    public void addPeer(Player player) {
        if (peers == null) peers = new HashSet<>();
        peers.add(player);
    }

    public String getDiscordTag() {
        return discordTag;
    }

    public void setDiscordTag(String discordTag) {
        this.discordTag = discordTag;
    }

    public List<Player> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<Player> reviewers) {
        this.reviewers = reviewers;
    }

    public void addReviewer(Player player) {
        if (reviewers == null) reviewers = new ArrayList<>();
        reviewers.add(player);
    }
}
