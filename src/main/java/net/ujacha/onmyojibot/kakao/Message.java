package net.ujacha.onmyojibot.kakao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Message {

	private String text;
	private Photo photo;

	@JsonProperty("message_button")
	private MessageButton messageButton;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public MessageButton getMessageButton() {
		return messageButton;
	}

	public void setMessageButton(MessageButton messageButton) {
		this.messageButton = messageButton;
	}

}
