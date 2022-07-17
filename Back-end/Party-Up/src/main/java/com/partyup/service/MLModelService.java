package com.partyup.service;

import com.partyup.model.Game;
import com.partyup.model.Player;
import com.partyup.payload.ProfileToken;
import com.partyup.payload.SuggestedPeerId;
import com.partyup.repository.PlayerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class MLModelService {

    private final PlayerRepository playerRepository;

    public MLModelService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<ProfileToken> getSuggestedProfileTokens(Player player, Game game) {
        String url = System.getenv("ML_URL");
        String findPeersUri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/" + player.getId())
                .path("/" + game.getId())
                .encode().toUriString();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SuggestedPeerId[]> suggestedPeerIds = restTemplate.getForEntity(findPeersUri, SuggestedPeerId[].class);
        return getProfileTokensOf(Arrays.asList(Objects.requireNonNull(suggestedPeerIds.getBody())));
    }

    private List<ProfileToken> getProfileTokensOf(List<SuggestedPeerId> suggestedPeerIds) {
        List<ProfileToken> profileTokens = new ArrayList<>();
        for (SuggestedPeerId peerId : suggestedPeerIds) {
            ProfileToken profileToken = new ProfileToken();
            Player player = playerRepository.findById(peerId.getId()).get();
            profileToken.setUsername(player.getUsername());
            profileToken.setProfilePicture(player.getProfilePicture());
            profileTokens.add(profileToken);
        }
        return profileTokens;
    }
}
