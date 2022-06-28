package com.partyup.model.posting;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "type")
	protected String type;
	@Lob
	@Column(name = "data", nullable = false)
	private byte[] file;

	public Content() {
		this.name = "";
		this.type = "";
		this.file = new byte[0];
	}

	public Content(String name, String type, byte[] image) {
		this.name = name;
		this.type = type;
		this.file = image;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || this.getClass() != other.getClass()) return false;
		Content otherImage = (Content) other;
		return Objects.equals(this.id, otherImage.id)
				&& Objects.equals(this.type, otherImage.type)
				&& Objects.equals(this.name, otherImage.name)
				&& Arrays.equals(this.file, otherImage.file);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
