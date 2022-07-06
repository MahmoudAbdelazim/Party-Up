package com.partyup.service;

import com.partyup.model.PeerRequest;
import com.partyup.model.Player;
import com.partyup.payload.ProfileToken;
import com.partyup.repository.PeerRequestRepository;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.exception.PeerRequestNotFoundException;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeersService {

    private PlayerRepository playerRepository;

    private PeerRequestRepository peerRequestRepository;

    @Autowired
    public PeersService(PlayerRepository playerRepository, PeerRequestRepository peerRequestRepository) {
        this.playerRepository = playerRepository;
        this.peerRequestRepository = peerRequestRepository;
    }

    public List<ProfileToken> getRequests() throws UserNotAuthenticatedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        List<ProfileToken> profileTokens = new ArrayList<>();
        for (PeerRequest peerRequest : player.getPeerRequests()) {
            profileTokens.add(new ProfileToken(peerRequest));
        }
        return profileTokens;
    }

    public String addPeer(String playerUsername) throws UserNotAuthenticatedException, PlayerNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        Optional<Player> otherPlayer = playerRepository.findByUsernameOrEmail(playerUsername, playerUsername);

        if (otherPlayer.isEmpty()) {
            throw new PlayerNotFoundException(playerUsername);
        }
        PeerRequest peerRequest = new PeerRequest();
        peerRequest.setUsername(player.getUsername());
        otherPlayer.get().addPeerRequest(peerRequest);
        playerRepository.save(otherPlayer.get());
        return "Peer Request is sent successfully";
    }

    public String respondPeerRequest(String playerUsername, String response)
            throws UserNotAuthenticatedException, PlayerNotFoundException, PeerRequestNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        Optional<Player> otherPlayer = playerRepository.findByUsernameOrEmail(playerUsername, playerUsername);

        if (otherPlayer.isEmpty()) {
            throw new PlayerNotFoundException(playerUsername);
        }

        PeerRequest peerRequest = null;
        for (PeerRequest pr : player.getPeerRequests()) {
            if (pr.getUsername().equals(playerUsername)) {
                peerRequest = pr;
            }
        }

        if (peerRequest == null) {
            throw new PeerRequestNotFoundException();
        }
        if (response.equalsIgnoreCase("accept")) {
            player.addPeer(otherPlayer.get());
            otherPlayer.get().addPeer(player);
        }
        player.getPeerRequests().remove(peerRequest);
        playerRepository.save(player);
        playerRepository.save(otherPlayer.get());
        peerRequestRepository.delete(peerRequest);
        return "Peer Responded Successfully";
    }

    public String unpeer(String playerUsername)
            throws UserNotAuthenticatedException, PlayerNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        Optional<Player> otherPlayer = playerRepository.findByUsernameOrEmail(playerUsername, playerUsername);

        if (otherPlayer.isEmpty()) {
            throw new PlayerNotFoundException(playerUsername);
        }

        if (player.hasPeer(otherPlayer.get())) {
            player.getPeers().remove(otherPlayer.get());
            otherPlayer.get().getPeers().remove(player);
            playerRepository.save(player);
            playerRepository.save(otherPlayer.get());
            return "Unpeered Successfully";
        }
        return "User is not a peer";
    }

    private String getUsername(Authentication authentication) {
        Object userSessionData = authentication.getPrincipal();
        String username;
        if (userSessionData instanceof UserDetails) {
            username = ((UserDetails) userSessionData).getUsername();
        } else {
            username = userSessionData.toString();
        }
        return username;
    }
}
