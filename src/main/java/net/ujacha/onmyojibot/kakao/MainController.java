package net.ujacha.onmyojibot.kakao;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@RequestMapping("/keyboard")
	public KeyboardResponse keyboard() {
		KeyboardResponse keyboardResponse = new KeyboardResponse();
		
		keyboardResponse.setType("text");
		
		return keyboardResponse;
	}
	
}
