package com.partyup.controller;

import com.partyup.model.posting.Content;
import com.partyup.service.PostContentService;
import com.partyup.service.exception.FileNotExistException;
import com.partyup.util.FileCompressionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/file")
public class FilesController {

	@Autowired
	private PostContentService postContentService;

	@GetMapping(path = {"/{id}"})
	public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) throws FileNotExistException {
		Content file = postContentService.getContentOf(id);
		return ResponseEntity
				.ok()
				.contentType(MediaType.valueOf(file.getType()))
				.body(FileCompressionUtility.decompressFile(file.getFile()));
	}

	@ExceptionHandler(FileNotExistException.class)
	public ResponseEntity<String> sendNotFound(FileNotExistException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
}
