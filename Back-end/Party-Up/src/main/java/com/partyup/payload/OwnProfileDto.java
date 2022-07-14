package com.partyup.payload;

import com.partyup.model.Country;
import com.partyup.model.posting.ContentData;

import java.util.ArrayList;
import java.util.List;

public class OwnProfileDto extends ProfileDto{
	String email;
	String firstName;
	String lastName;
	String discordTag;

	Country country;
	List<HandleDto> handles = new ArrayList<>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<HandleDto> getHandles() {
		return handles;
	}

	public void setHandles(List<HandleDto> handles) {
		this.handles = handles;
	}

	public void setDiscordTag(String discordTag) {
		this.discordTag = discordTag;
	}

	public String getDiscordTag() {
		return discordTag;
	}

	public ContentData getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(ContentData profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
