package com.partyup.service;

import com.partyup.model.Player;
import com.partyup.model.posting.Content;
import com.partyup.model.posting.Post;
import com.partyup.payload.PostDto;
import com.partyup.repository.PostRepository;
import com.partyup.service.exception.PostNotFoundException;
import com.partyup.util.ImageCompressionUtility;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostingService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	ModelMapper postingMapper;

	public long savePostOfUser(PostDto postDto, Player player) {
		Post post = postingMapper.map(postDto, Post.class);
		post.setPlayer(player);
		postRepository.save(post);
		return post.getId();
	}

	public PostDto getPostOfId(Long id) throws PostNotFoundException {
		Optional<Post> optionalPost = postRepository.findById(id);
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			return postingMapper.map(post, PostDto.class);
		} else {
			throw new PostNotFoundException(Long.toString(id));
		}
	}

	public List<PostDto> getPostsOfUser(Player player) {
		//TODO
		return new ArrayList<PostDto>();
	}

	public List<PostDto> getPostsRelatedToUser(Player player) {
		//TODO
		return new ArrayList<PostDto>();
	}

}

@Configuration
@Slf4j
class configPostingService {
	@Bean
	ModelMapper postingMapper() {
		ModelMapper postingMapper = new ModelMapper();

		var toPost = postingMapper.typeMap(PostDto.class, Post.class);
		var fromMpFileToContent = getToPostConverter();
		toPost.addMapping(PostDto::getText, Post::setText);
		toPost.addMappings(mapper -> mapper.using(fromMpFileToContent).map(PostDto::getFiles, Post::setContents));

		var toDto = postingMapper.typeMap(Post.class, PostDto.class);
		var fromContentToMpFile = getToDtoConverter();
		toDto.addMapping(Post::getText, PostDto::setText);
		toDto.addMappings(mapper -> mapper.using(fromContentToMpFile).map(Post::getContents, PostDto::setFiles));
		return postingMapper;
	}

	Converter<List<MultipartFile>, List<Content>> getToPostConverter() {
		return src -> {
			try {
				var files = src.getSource();
				List<Content> contents = new ArrayList<>();
				if (files != null) {
					for (MultipartFile file : files) {
						byte[] compressedFile = ImageCompressionUtility.compressImage(file.getBytes());
						var content = new Content(file.getOriginalFilename(), file.getContentType(), compressedFile);
						contents.add(content);
					}
				}
				return contents;
			} catch (IOException e) {
				log.error("Couldn't read the uploaded file: " + e);
				return new ArrayList<>();
			}
		};
	}

	Converter<List<Content>, List<MultipartFile>> getToDtoConverter() {
		return src -> {
			var contents = src.getSource();
			List<MultipartFile> files = new ArrayList<>();
			if (contents != null) {
				for (Content file : contents) {
					byte[] decompressedFile = ImageCompressionUtility.decompressImage(file.getFile());
					MultipartFile mpFile = new MockMultipartFile(file.getName(), file.getName(), file.getType(), decompressedFile);
					files.add(mpFile);
				}
			}
			return files;
		};
	}
}
