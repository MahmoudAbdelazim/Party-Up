package com.partyup.controller;

import com.partyup.model.posting.Image;
import com.partyup.payload.UploadResponse;
import com.partyup.repository.ImageRepository;
import com.partyup.util.ImageCompressionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping("/api/image")
public class ImageUploadController {

	@Autowired
	ImageRepository imageRepository;

	@PostMapping("/upload")
	public ResponseEntity<UploadResponse> uploadImage(@RequestParam("image") MultipartFile file)
			throws IOException {

		Image image = new Image(
				file.getOriginalFilename(),
				file.getContentType(),
				ImageCompressionUtility.compressImage(file.getBytes())
		);

		imageRepository.save(image);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new UploadResponse("Image uploaded successfully: " + file.getOriginalFilename() + " id: " + image.getId()));
	}


	@GetMapping(path = {"/get/{id}"})
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {

		final Optional<Image> image = imageRepository.findById(id);
		return ResponseEntity
				.ok()
				.contentType(MediaType.valueOf(image.get().getType()))
				.body(ImageCompressionUtility.decompressImage(image.get().getImage()));
	}
}