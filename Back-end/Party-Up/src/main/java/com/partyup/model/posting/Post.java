package com.partyup.model.posting;

import com.partyup.model.Player;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Post {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String text;
	@OneToMany(cascade = CascadeType.ALL)
	private List<ContentData> contents;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@NotNull
	private Player player;

	@CreationTimestamp
	private Date createAt;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ContentData> getContents() {
		return contents;
	}

	public void setContents(List<ContentData> contents) {
		this.contents = contents;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return createAt;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || this.getClass() != o.getClass()) return false;
		Post other = (Post) o;
		return Objects.equals(other.id, this.id)
				&& Objects.equals(other.player, this.player)
				&& Objects.equals(other.text, this.text)
				&& contents.equals(other.contents);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
