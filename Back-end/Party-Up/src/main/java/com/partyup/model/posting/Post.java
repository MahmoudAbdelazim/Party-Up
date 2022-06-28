package com.partyup.model.posting;

import com.partyup.model.Player;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@ManyToOne
	@NotNull
	private Player player;

	private String text;

	@OneToMany
	private List<Content> contents;

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

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public long getId() {
		return id;
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
