package com.partyup.repository;

import com.partyup.model.Player;
import com.partyup.model.posting.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,String> {
	@Override
	Optional<Post> findById(String id);

	List<Post> findAllByPlayer(Player player);

	Page<Post> findAllByPlayerOrderByCreateAt(Player player, Pageable pageable);

	Page<Post> findAllByPlayerInOrderByCreateAt(List<Player> ids, Pageable pageable);

}
