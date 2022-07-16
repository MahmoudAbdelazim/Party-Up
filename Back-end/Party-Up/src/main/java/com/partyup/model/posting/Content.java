package com.partyup.model.posting;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "file")
public class Content {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	@Column(name = "name")
	private String name;
	@Column(name = "type")
	private String type;
	@Column(name = "size")
	private Long size;
	@Lob
	@Column(name = "data", nullable = false)
	private byte[] file;

	public Content() {
		this.name = "";
		this.type = "";
		this.size = 0L;
		this.file = new byte[0];
	}

	public Content(String name, String type, byte[] file) {
		this.name = name;
		this.type = type;
		this.file = file;
		this.size = (long) file.length;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSize() {
		return size;
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
		Content otherContent = (Content) other;
		return Objects.equals(this.id, otherContent.id)
				&& Objects.equals(this.type, otherContent.type)
				&& Objects.equals(this.name, otherContent.name)
				&& Arrays.equals(this.file, otherContent.file);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
