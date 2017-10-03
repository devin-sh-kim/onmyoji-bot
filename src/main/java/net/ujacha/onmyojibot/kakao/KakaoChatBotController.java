package net.ujacha.onmyojibot.kakao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kakao")
public class KakaoChatBotController {

	
	private static final Logger log = LoggerFactory.getLogger(KakaoChatBotController.class);

	
	@GetMapping("keyboard")
	public Keyboard keyboard() {
		Keyboard keyboard = new Keyboard();

		keyboard.setType("text");

		return keyboard;
	}

	@PostMapping("message")
	public KakaoMessageResponse message(@RequestBody KakaoRequestBody requestBody) {
		
		log.debug("USERKEY:{} TYPE:{} CONTENT:{}", requestBody.getUserKey(), requestBody.getType(), requestBody.getContent());
		
		KakaoMessageResponse messageResponse = new KakaoMessageResponse();

		Message message = new Message();
		message.setText("ECHO: " + requestBody.getContent());
		messageResponse.setMessage(message );
		
		return messageResponse;
	}

}
