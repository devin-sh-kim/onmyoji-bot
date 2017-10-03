package net.ujacha.onmyojibot.kakao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kakao")
public class KakaoChatBotController {

	
	private static final Logger log = LoggerFactory.getLogger(KakaoChatBotController.class);

	// Home Keyboard API
	// curl -XGET 'https://:your_server_url/keyboard'
	@GetMapping("keyboard")
	public Keyboard keyboard() {
		Keyboard keyboard = new Keyboard();

		keyboard.setType("text");

		return keyboard;
	}
	
	// 메시지 수신 및 자동응답 API
	//	curl -XPOST 'https://:your_server_url/message' -d '{
	//	  "user_key": "encryptedUserKey",
	//	  "type": "text",
	//	  "content": "차량번호등록"
	//	}'	
	// curl -XPOST 'https://your_server_url/message' -d '{
	//	  "user_key": "encryptedUserKey",
	//	  "type": "photo",
	//	  "content": "http://photo_url/number.jpg"
	//	}'
	@PostMapping("message")
	public KakaoMessageResponse message(@RequestBody KakaoRequestBody requestBody) {
		
		log.debug("USERKEY:{} TYPE:{} CONTENT:{}", requestBody.getUserKey(), requestBody.getType(), requestBody.getContent());
		
		KakaoMessageResponse messageResponse = new KakaoMessageResponse();

		Message message = new Message();
		message.setText("ECHO: " + requestBody.getContent());
		messageResponse.setMessage(message );
		
		return messageResponse;
	}
	
	// 친구 추가/차단 알림 API
	// curl -XPOST 'https://:your_server_url/friend' -d '{"user_key" : "HASHED_USER_KEY" }'
	@PostMapping("friend")
	public void addFriend() {
		
	} 
	
	// curl -XDELETE 'https://:your_server_url/friend/:user_key'
	@DeleteMapping("friend/{userKey}")
	public void deleteFriend(@PathVariable String userKey) {
		
	}
	
	// 채팅방 나가기
	// curl -XDELETE 'https://:your_server_url/chat_room/HASHED_USER_KEY'
	@DeleteMapping("chat_room/{userKey}")
	public void deletechatRoom(@PathVariable String userKey) {
		
	}
	
}
