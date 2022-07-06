/**
package com.partyup;

import com.partyup.model.Player;
import com.partyup.model.posting.Content;
import com.partyup.model.posting.Post;
import com.partyup.payload.PostDto;
import com.partyup.service.PostingService;
import com.partyup.util.ImageCompressionUtility;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PartyUpApplicationTests {

	@Autowired
	PostingService postingService;

	PostDto postDto;

	Post post;

	@Test
	@Order(1)
	public void contextLoads() {
	}

	public void initializePostDto() {
		postDto = new PostDto();
		List<MultipartFile> files = new ArrayList<>();
		for (int i = 0; i < 5; ++i) {
			var file = new MockMultipartFile(
					"file" + i,
					"file" + i,
					"jpg",
					new byte[]{1, 2, 3, 4}
			);
			files.add(file);
		}
		postDto.setText("This is the first post on this platform. Hello World!");
		postDto.setFiles(files);
	}

	void initializePost(@Autowired ModelMapper postMapper) {
		initializePostDto();
		post = postMapper.map(postDto, Post.class);
	}

	@Test
	@Order(2)
	public void testMapperDtoToPost(@Autowired ModelMapper postMapper) {
		initializePostDto();
		Post newPost = postMapper.map(postDto, Post.class);
		post = new Post();
		post.setText(postDto.getText());
		List<Content> contents = new ArrayList<>();
		for (int i = 0; i < 5; ++i) {
			var content = new Content(
					"file" + i,
					"jpg",
					ImageCompressionUtility.compressImage(new byte[]{1, 2, 3, 4})
			);
			contents.add(content);
		}
		post.setContents(contents);
		assertEquals(post, newPost);
	}
	@Test
	@Order(3)
	public void testUploadToPostingService() {
		initializePostDto();
		Player player = new Player();
		long postId = postingService.savePostOfUser(postDto, player);
		try {
			PostDto newPostDto = postingService.getPostOfId(postId);
			assertEquals(postDto, newPostDto);
		} catch (Exception e) {
			fail();
		}
	}
}
*/
