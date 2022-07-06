package com.partyup.payload;

import com.partyup.model.posting.ContentData;
import com.partyup.model.posting.Post;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

public class PostResource extends RepresentationModel<PostResource> {
	private String text;
	private List<ContentData> contents;

	private Date createdAt;

	public PostResource(Post post) {
		this.text = post.getText();
		this.contents = post.getContents();
		this.createdAt = post.getCreationDate();
	}

	public List<ContentData> getContents() {
		return contents;
	}

	public String getText() {
		return text;
	}

	public Date getCreationDate() {
		return createdAt;
	}

}
