package net.ujacha.onmyojibot.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Shikigami {

	@Id
	private Long id;
	private String name;
	private String[] hints;
	private String rarity;
	private Location[] locations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getHints() {
		return hints;
	}

	public void setHints(String[] hints) {
		this.hints = hints;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public Location[] getLocations() {
		return locations;
	}

	public void setLocations(Location[] locations) {
		this.locations = locations;
	}

}
