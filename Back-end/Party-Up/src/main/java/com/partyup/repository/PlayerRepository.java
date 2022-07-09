package com.partyup.repository;

import com.partyup.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(String email);
    Optional<Player> findByUsernameOrEmail(String username, String email);
    Optional<Player> findByUsername(String username);
    List<Player> findAllByEmail(String email);
    List<Player> findAllByUsername(String username);
    List<Player> findAllByDiscordTag(String discordTag);
    List<Player> findAllByIdIn(List<Long> ids);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByDiscordTag(String discordTag);
}
