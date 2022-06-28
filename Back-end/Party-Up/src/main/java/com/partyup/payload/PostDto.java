package com.partyup.payload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PostDto {
	private String text;
	private List<MultipartFile> files;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
}
