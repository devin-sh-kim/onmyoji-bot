package net.ujacha.onmyojibot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Location implements Comparable<Location> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String type;
	private String value;
	private int count;

	public Location() {
		// TODO Auto-generated constructor stub
	}

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public int compareTo(Location o) {
		
		if (StringUtils.equals(this.type, o.type)) {
			if (StringUtils.equals(this.value, o.value)) {
				return 0;
			} else {

				String s1 = this.value.replace("(어려움)", "").replace("(보통)", "");
				String s2 = o.value.replace("(어려움)", "").replace("(보통)", "");

				if (StringUtils.isNumeric(s1) && StringUtils.isNumeric(s2)) {
					return Integer.parseInt(s1) - Integer.parseInt(s2);
				} else {
					return this.value.compareToIgnoreCase(o.value);
				}

			}

		} else {
			return this.type.compareToIgnoreCase(o.type);
		}

	}

}
