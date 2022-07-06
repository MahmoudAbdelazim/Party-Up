package com.partyup.assembler;

import com.partyup.controller.PostingController;
import com.partyup.model.posting.Post;
import com.partyup.payload.PostResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class PostResourceAssembler extends RepresentationModelAssemblerSupport<Post, PostResource> {

	public PostResourceAssembler() {
		super(PostingController.class, PostResource.class);
	}

	@Override
	protected PostResource instantiateModel(Post post) {
		return new PostResource(post);
	}

	@Override
	public PostResource toModel(Post post) {
		return createModelWithId(post.getId(), post);
	}
}
