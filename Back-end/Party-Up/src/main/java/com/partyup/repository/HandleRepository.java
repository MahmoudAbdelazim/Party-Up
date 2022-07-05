package com.partyup.repository;

import com.partyup.model.Game;
import com.partyup.model.Handle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HandleRepository extends JpaRepository<Handle, Long> {
    public Optional<Handle> findByHandleNameAndGame(String handle, Game game);
}
