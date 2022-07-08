package com.partyup.repository;

import com.partyup.model.Player;
import com.partyup.model.posting.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,String> {
	@Override
	Optional<Post> findById(String id);

	List<Post> findAllByPlayer(Player player);

	Slice<Post> findAllByPlayerAndOrderByCreateAt(Player player, Pageable pageable);

}
