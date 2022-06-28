package com.partyup.repository;

import com.partyup.model.posting.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	@Override
	Optional<Image> findById(Long id);
}
