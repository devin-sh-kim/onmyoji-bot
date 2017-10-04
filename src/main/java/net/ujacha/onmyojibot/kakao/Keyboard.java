package net.ujacha.onmyojibot.kakao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;;

@JsonInclude(Include.NON_NULL)
public class Keyboard {

	private String type;
	private String[] buttons;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getButtons() {
		return buttons;
	}

	public void setButtons(String[] buttons) {
		this.buttons = buttons;
	}

	
	
	
}
