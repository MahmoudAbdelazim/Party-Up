package com.partyup.assembler;

import com.partyup.controller.PostingController;
import com.partyup.model.posting.Post;
import com.partyup.payload.PostModel;
import com.partyup.payload.ProfileToken;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PostModelAssembler extends RepresentationModelAssemblerSupport<Post, PostModel> {

	public PostModelAssembler() {
		super(PostingController.class, PostModel.class);
	}

	@Override
	protected PostModel instantiateModel(Post post) {
		ProfileToken owner = new ProfileToken(post.getPlayer().getUsername(), post.getPlayer().getProfilePicture());
		return new PostModel(post, owner);
	}

	@Override
	public PostModel toModel(Post post) {
		return createModelWithId(post.getId(), post);
	}
}
