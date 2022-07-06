package com.partyup.model.posting;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URL;
import java.util.Objects;

@Entity
public class ContentData {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String type;
	private long size;

	private URL url;

	public ContentData() {
		this.type = "";
		this.size = 0L;
		this.url = null;
	}

	public ContentData(String type, long size, URL url) {
		this.type = type;
		this.size = size;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public long getSize() {
		return size;
	}

	public String getType() {
		return type;
	}

	public URL getURL() {
		return url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || this.getClass() != o.getClass()) return false;
		ContentData other = (ContentData) o;
		return Objects.equals(this.id, other.id)
				&& Objects.equals(this.type, other.type)
				&& Objects.equals(this.size, other.size)
				&& Objects.equals(this.url, other.url);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}

