package net.ujacha.onmyojibot.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Location {

	@Id
	private Long id;
	private String type;
	private int value;
	private int count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
