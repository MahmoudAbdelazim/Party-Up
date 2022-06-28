package com.partyup.model.posting;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "image")
@Getter
@Setter
@ToString
public class Image {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "name")
	protected String name;
	@Column(name = "type")
	protected String type;
	@Column(name = "image", nullable = false, length = 100000)
	private byte[] image;

	public Image() {
		this.name = "";
		this.type = "";
		this.image = new byte[0];
	}
	public Image(String name, String type, byte[] image) {
		this.name = name;
		this.type = type;
		this.image = image;
	}

	public byte[] getImage() {
		return image;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || this.getClass() != other.getClass()) return false;
		Image otherImage = (Image) other;
		return id != null && Objects.equals(id, otherImage.id);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
