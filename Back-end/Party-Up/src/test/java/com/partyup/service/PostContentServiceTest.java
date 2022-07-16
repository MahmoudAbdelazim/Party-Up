package com.partyup.service;

import com.partyup.model.posting.Content;
import com.partyup.model.posting.ContentData;
import com.partyup.repository.ContentRepository;
import com.partyup.service.exception.FileNotExistException;
import com.partyup.service.exception.UploadFailedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
class PostContentServiceTest {

	private static List<MultipartFile> files;

	private static List<ContentData> savedFiles;

	private static List<Content> contents;

	private static UriComponentsBuilder uriComponentsBuilder;

	@Mock
	private ContentRepository postContentRepo;

	@InjectMocks
	private PostContentService postContentService;

	@BeforeAll
	static void initialize() throws MalformedURLException {
		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponentsBuilder.scheme("https").host("www.partyup.com").path("/api/file");
		files = new ArrayList<>();
		savedFiles = new ArrayList<>();
		contents = new ArrayList<>();
		for (int i = 0; i < 3; ++i) {
			String filename = "file" + (i + 1);
			byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			var uriClone = (UriComponentsBuilder) uriComponentsBuilder.clone();

			var f = new MockMultipartFile(filename, data);
			var content = new Content(filename, "image/png", data);
			content.setId("" + (i + 1));
			String uri = uriClone.path("/" + content.getId()).encode().toUriString();

			var contentData = new ContentData("image/png", data.length, new URL(uri));
			files.add(f);
			savedFiles.add(contentData);
			contents.add(content);
		}
	}

	@BeforeEach
	void setUp() throws IOException {
		MockitoAnnotations.openMocks(this);
		when(postContentRepo.saveAll(any())).thenReturn(contents);
		var f = files.get(0);
		Content content = new Content(f.getName(), f.getContentType(), f.getBytes());
		when(postContentRepo.findById(any(String.class))).thenReturn(Optional.of(content));
	}

	@Test
	void saveContents() throws UploadFailedException {
		List<ContentData> contentData = postContentService.saveContents(files, uriComponentsBuilder);
		assertEquals(contentData, savedFiles);
	}

	@Test
	void saveContentsFailed() {

	}

	@Test
	void getContentOf() throws FileNotExistException {
		for (var content : contents) {
			when(postContentRepo.findById(any(String.class))).thenReturn(Optional.ofNullable(content));
			assertEquals(content, postContentService.getContentOf(content.getId()));
		}
	}
	@Test
	void getContentOfFailed()  {
		when(postContentRepo.findById("100")).thenReturn(Optional.empty());
		assertThrows(FileNotExistException.class, () -> postContentService.getContentOf("100"));
	}
}