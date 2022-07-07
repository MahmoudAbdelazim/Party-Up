package com.partyup.controller;

import com.partyup.payload.ProfileToken;
import com.partyup.service.PeersService;
import com.partyup.service.exception.GameNotFoundException;
import com.partyup.service.exception.PeerRequestNotFoundException;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PeersController {

    private final PeersService peersService;

    @Autowired
    public PeersController(PeersService peersService) {
        this.peersService = peersService;
    }

    @GetMapping("/api/peerRequests")
    public ResponseEntity<List<ProfileToken>> peerRequests()
            throws UserNotAuthenticatedException {
        return ResponseEntity.ok(peersService.getRequests());
    }

    @PostMapping("/api/addPeer/{username}")
    public ResponseEntity<String> addPeer(@PathVariable String username)
            throws UserNotAuthenticatedException, PlayerNotFoundException {
        return ResponseEntity.ok(peersService.addPeer(username));
    }

    @PostMapping("/api/respondPeerRequest/{username}")
    public ResponseEntity<String> respondPeerRequest(@PathVariable String username, @RequestBody String response)
            throws PeerRequestNotFoundException, UserNotAuthenticatedException, PlayerNotFoundException {
        return ResponseEntity.ok(peersService.respondPeerRequest(username, response));
    }

    @PostMapping("/api/unpeer/{username}")
    public ResponseEntity<String> unpeer(@PathVariable String username)
            throws UserNotAuthenticatedException, PlayerNotFoundException {
        return ResponseEntity.ok(peersService.unpeer(username));
    }

    @GetMapping("/api/findPeers")
    public ResponseEntity<List<ProfileToken>> findPeers(@RequestHeader("gameName") String gameName)
            throws UserNotAuthenticatedException, GameNotFoundException {
        return ResponseEntity.ok(peersService.findPeers(gameName));
    }

    @GetMapping("/api/peers")
    public ResponseEntity<List<ProfileToken>> myPeers()
            throws UserNotAuthenticatedException {
        return ResponseEntity.ok(peersService.myPeers());
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<String> sendUserNotAuthenticated(UserNotAuthenticatedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> sendPlayerNotFound(PlayerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @ExceptionHandler(PeerRequestNotFoundException.class)
    public ResponseEntity<String> sendPeerRequestNotFound(PeerRequestNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
