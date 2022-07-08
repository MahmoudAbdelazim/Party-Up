package com.partyup.payload;

import com.partyup.model.posting.ContentData;
import com.partyup.model.posting.Post;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

public class PostModel extends RepresentationModel<PostModel> {
	private String text;
	private List<ContentData> contents;
	private Date createdAt;

	private ProfileToken owner;

	public PostModel(Post post, ProfileToken owner) {
		this.text = post.getText();
		this.contents = post.getContents();
		this.createdAt = post.getCreationDate();
		this.owner = owner;
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

	public ProfileToken getOwner() {
		return owner;
	}



}
