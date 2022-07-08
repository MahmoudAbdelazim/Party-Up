package com.partyup.payload;

import com.partyup.model.posting.ContentData;

public abstract class ProfileDto {

	protected String username;
	protected ContentData profilePicture;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ContentData getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(ContentData profilePicture) {
		this.profilePicture = profilePicture;
	}
}
