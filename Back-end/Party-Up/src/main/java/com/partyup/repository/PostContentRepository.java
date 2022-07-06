package com.partyup.repository;

import com.partyup.model.posting.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostContentRepository extends JpaRepository<Content, String> {

}
