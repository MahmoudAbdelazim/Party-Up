package com.partyup.service;

import com.partyup.model.posting.Content;
import com.partyup.model.posting.ContentData;
import com.partyup.repository.ContentRepository;
import com.partyup.service.exception.FileNotExistException;
import com.partyup.service.exception.UploadFailedException;
import com.partyup.util.FileCompressionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostContentService {
	@Autowired
	private ContentRepository postContentRepo;

	public List<ContentData> saveContents(List<MultipartFile> files, UriComponentsBuilder uriBuilder) throws UploadFailedException {
		List<Content> contents = new ArrayList<>();
		List<ContentData> contentsData = new ArrayList<>();
		try {
			if (files != null && !files.isEmpty()) {
				for (var file : files) {
					var content = new Content(
							file.getName(),
							file.getContentType(),
							FileCompressionUtility.compressFile(file.getBytes())
					);
					contents.add(content);
				}

				contents = postContentRepo.saveAll(contents);

				for (var content : contents) {
					URL fileURL = getURLOf(content, uriBuilder);
					contentsData.add(new ContentData(content.getType(), content.getSize(), fileURL));
				}
			}
			return contentsData;

		} catch (IOException e) {
			var specificException = new UploadFailedException();
			specificException.setStackTrace(e.getStackTrace());
			throw specificException;
		}
	}

	private URL getURLOf(Content content, UriComponentsBuilder uriBuilder) throws UploadFailedException {
		try {
			var uriClone = (UriComponentsBuilder) uriBuilder.clone();
			String uri = uriClone.path("/" + content.getId()).encode().toUriString();
			return new URL(uri);
		} catch (MalformedURLException e) {
			var specific = new UploadFailedException("Couldn't generate URLs");
			specific.setStackTrace(e.getStackTrace());
			throw specific;
		}
	}

	public Content getContentOf(String id) throws FileNotExistException {
		Optional<Content> content = postContentRepo.findById(id);
		if (content.isEmpty()) throw new FileNotExistException();
		return content.get();
	}
}



