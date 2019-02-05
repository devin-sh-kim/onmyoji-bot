package net.ujacha.onmyojibot.kakao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.ujacha.onmyojibot.entity.SecretLetter;
import net.ujacha.onmyojibot.log.LogService;
import net.ujacha.onmyojibot.repository.SecretLetterRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ujacha.onmyojibot.entity.Location;
import net.ujacha.onmyojibot.entity.Shikigami;
import net.ujacha.onmyojibot.repository.ShikigamiRepository;

@RestController
@RequestMapping("kakao")
@Slf4j
public class KakaoChatBotController {

	@Autowired
	private OnmyojiBotService onmyojiBotService;

	@Autowired
	private LogService logService;

//	private static final Logger log = LoggerFactory.getLogger(KakaoChatBotController.class);


	// Home Keyboard API
	// curl -XGET 'https://:your_server_url/keyboard'
	@GetMapping("keyboard")
	public Keyboard keyboard() {
		Keyboard keyboard = new Keyboard();

		// keyboard.setType("text");

		keyboard.setType("buttons");
		keyboard.setButtons(new String[] { OnmyojiBotService.START_TEXT });

		return keyboard;
	}

	// 메시지 수신 및 자동응답 API
	// curl -XPOST 'https://:your_server_url/message' -d '{
	// "user_key": "encryptedUserKey",
	// "type": "text",
	// "content": "차량번호등록"
	// }'
	// curl -XPOST 'https://your_server_url/message' -d '{
	// "user_key": "encryptedUserKey",
	// "type": "photo",
	// "content": "http://photo_url/number.jpg"
	// }'
	@PostMapping("message")
	public KakaoMessageResponse message(@RequestBody KakaoRequestBody requestBody) {

//		log.debug("USERKEY:{} TYPE:{} CONTENT:{}", requestBody.getUserKey(), requestBody.getType(), requestBody.getContent());
		
		KakaoMessageResponse messageResponse = new KakaoMessageResponse();
		Message message;

		if (StringUtils.equals(OnmyojiBotService.START_TEXT, requestBody.getContent())) {
			message = onmyojiBotService.buildFindInfo();
		} else if (StringUtils.equals("다시검색", requestBody.getContent())) {
			message = onmyojiBotService.buildFindInfo();
		} else if (StringUtils.equals(OnmyojiBotService.CREW_BUTTON_TEXT, requestBody.getContent())) {
			message = onmyojiBotService.buildCrewInfo();
		} else {
			// 식신 검색
			List<Shikigami> shikigamis = onmyojiBotService.findShikigamis(requestBody.getContent());

			// 밀서 검색
			List<SecretLetter> secretLetters = onmyojiBotService.findSecretLetters(requestBody.getContent());

			message = onmyojiBotService.buildMessage(shikigamis, secretLetters);
			messageResponse.setKeyboard(onmyojiBotService.buildKeyboardByShikigamis(shikigamis));

			boolean isFound = false;
			if (shikigamis != null){
				isFound = true;
			}

			log.trace("USERKEY:{}\tQUERY:{}\tFIND:{}", requestBody.getUserKey(), requestBody.getContent(), isFound ? shikigamis.stream().map(s -> s.getName()).collect(Collectors.joining(", ")) : "Not Found");

			if(secretLetters != null){
				isFound = true;
			}

			if(secretLetters != null && secretLetters.size() > 0){
				secretLetters.forEach(s -> {
					log.trace("USERKEY:{}\tQUERY:{}\tSECRET:{}", requestBody.getUserKey(), requestBody.getContent(), secretLetters != null ? s.getQuestion() + ":" + s.getAnswer() : "Not Found");
				});
			}

			if(!isFound){
				logService.logNotFound(requestBody.getUserKey(), requestBody.getContent());
			}
		}

		messageResponse.setMessage(message);

		return messageResponse;
	}





	// 친구 추가/차단 알림 API
	// curl -XPOST 'https://:your_server_url/friend' -d '{"user_key" :
	// "HASHED_USER_KEY" }'
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
