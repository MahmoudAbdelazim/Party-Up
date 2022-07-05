package com.partyup.repository;

import com.partyup.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    public Optional<Game> findByName(String name);
}
