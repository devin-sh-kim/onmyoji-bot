package net.ujacha.onmyojibot.kakao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kakao")
public class MainController {

	@GetMapping("keyboard")
	public KeyboardResponse keyboard() {
		KeyboardResponse keyboardResponse = new KeyboardResponse();
		
		keyboardResponse.setType("text");
		
		return keyboardResponse;
	}
	
}
