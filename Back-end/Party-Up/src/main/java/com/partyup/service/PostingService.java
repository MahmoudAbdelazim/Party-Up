package com.partyup.service;

import com.partyup.model.Player;
import com.partyup.model.posting.Post;
import com.partyup.payload.PostUploadDto;
import com.partyup.repository.PostRepository;
import com.partyup.service.exception.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostingService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	ModelMapper postingMapper;

	public String savePostOfUser(PostUploadDto postUploadDto, Player player) {
		Post post = postingMapper.map(postUploadDto, Post.class);
		post.setPlayer(player);
		postRepository.save(post);
		return post.getId();
	}

	public Post getPostOfId(String id) throws PostNotFoundException {
		Optional<Post> optionalPost = postRepository.findById(id);
		if (optionalPost.isPresent()) {
			return optionalPost.get();
		} else {
			throw new PostNotFoundException(id);
		}
	}

	public List<Post> getPostsOfUser(Player player) {
		return postRepository.findAllByPlayer(player);
	}

	public List<Post> getPostsRelatedToUser(Player player) {
		//TODO
		return new ArrayList<Post>();
	}

}

@Configuration
@Slf4j
class configPostingService {
	@Bean
	ModelMapper postingMapper() {
		ModelMapper postingMapper = new ModelMapper();
		var toPost = postingMapper.typeMap(PostUploadDto.class, Post.class);
		toPost.addMapping(PostUploadDto::getText, Post::setText);
		toPost.addMapping(PostUploadDto::getContents, Post::setContents);
		return postingMapper;
	}
}
