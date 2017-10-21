package net.ujacha.onmyojibot.kakao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
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
public class KakaoChatBotController {

	@Autowired
	private ShikigamiRepository shikigamiRepository;

	private static final Logger log = LoggerFactory.getLogger(KakaoChatBotController.class);

	private static final String WELCOME_MESSAGE = 
			"찾는 식신의 이름을 입력하세요.\n" + 
			">  황도깨비\n" + 
			"\n" + 
			"초성으로 찾아볼까요?\n" + 
			">  ㅎㄷㄲㅂ\n" + 
			"\n" + 
			"신비 요괴 힌트를 입력하세요.\n" + 
			"(힌트는 하나씩만 입력해 주세요/힌트 초성은 아직...)\n" +
			">  허수아비\n" + 
			">  명계\n" +
			"\n" + 
			"즐거운 게임생활 되세요!";

	// Home Keyboard API
	// curl -XGET 'https://:your_server_url/keyboard'
	@GetMapping("keyboard")
	public Keyboard keyboard() {
		Keyboard keyboard = new Keyboard();

		// keyboard.setType("text");

		keyboard.setType("buttons");
		keyboard.setButtons(new String[] { "식신찾기" });

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

		KakaoMessageResponse messageResponse = new KakaoMessageResponse();
		Message message = null;

		log.debug("USERKEY:{} TYPE:{} CONTENT:{}", requestBody.getUserKey(), requestBody.getType(),
				requestBody.getContent());

		if (StringUtils.equals("식신찾기", requestBody.getContent())) {
			message = buildFindInfo();
		} else {
			List<Shikigami> shikigamis = findShikigamis(requestBody.getContent());
			message = buildMessage(shikigamis);
			messageResponse.setKeyboard(buildKeyboard(shikigamis));
		}

		messageResponse.setMessage(message);

		return messageResponse;
	}

	private Keyboard buildKeyboard(List<Shikigami> shikigamis) {

		Keyboard keyboard = new Keyboard();
		keyboard.setType("text");
		
		if (shikigamis != null) {
			if (shikigamis.size() > 1) {
				keyboard.setType("buttons");

				String[] buttons = shikigamis.stream().map(s -> s.getName()).toArray(String[]::new);

				keyboard.setButtons(buttons);
			}
		}

		return keyboard;
	}

	private Message buildFindInfo() {

		Message message = new Message();

		message.setText(WELCOME_MESSAGE);

		return message;
	}

	private Message buildMessage(List<Shikigami> shikigamis) {
		Message message = new Message();

		if (shikigamis != null) {

			// if (StringUtils.isNotEmpty(shikigami.getInfoPageUrl())) {
			// MessageButton messageButton = new MessageButton();
			// messageButton.setLabel("정보 보기");
			// messageButton.setUrl(shikigami.getInfoPageUrl());
			// message.setMessageButton(messageButton);
			// }

			// if (StringUtils.isNotEmpty(shikigami.getImageUrl())) {
			// Photo photo = new Photo();
			// photo.setUrl(shikigami.getImageUrl());
			// photo.setHeight(720);
			// photo.setWidth(630);
			// message.setPhoto(photo);
			// }

			String text = "";
			if (shikigamis.size() > 1) {

				text = buildSelectShikigamiMassage(shikigamis);
				message.setText(text);

			} else {
				text = buildShikigamiMessageText(shikigamis.get(0));
				message.setText(text);

			}

		} else {
			// 없음
			message.setText("찾는 식신이 없습니다. 다시 한번 확인해주세요.");
		}

		return message;
	}

	private String buildSelectShikigamiMassage(List<Shikigami> shikigamis) {

		StringBuffer sb = new StringBuffer();

		sb.append("다음 식신을 찾았습니다.\n\n");

		shikigamis.forEach(s -> {
			sb.append("- ").append(s.getName()).append("\n");
		});

		sb.append("\n찾고있는 식신을 선택하세요.");
		
		return sb.toString();
	}

	private List<Shikigami> findShikigamis(String content) {

		String text = content.trim();

		List<Shikigami> find = shikigamiRepository.findByHint(text);

		find.addAll(shikigamiRepository.findByName(text));

		find.addAll(shikigamiRepository.findByInitialName(text));

		HashSet<Shikigami> hashSet = new HashSet<>(find);
		
		find = new ArrayList<>(hashSet);		
		
		if (find != null && find.size() > 0) {
			return find;
		}
		return null;
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

	private String buildShikigamiMessageText(Shikigami shikigami) {
		StringBuffer sb = new StringBuffer();

		Location[] locations = shikigami.getLocations();

		int max = 0;
		Location recommend = null;
		for (Location l : locations) {

			if (StringUtils.equals("탐험", l.getType()) && StringUtils.isNumeric(l.getValue())
					&& Integer.parseInt(l.getValue()) > 18) {
				continue;
			}

			if (l.getCount() > max) {
				max = l.getCount();
				recommend = l;
			}

		}

		sb.append("찾은 식신: [").append(shikigami.getName()).append("]\n\n");
		if (recommend != null) {
			sb.append("추천 위치:\n");
			sb.append(buildLocation(recommend)).append("\n");
		}

		sb.append("출연 위치:\n");
		for (Location l : locations) {
			sb.append(buildLocation(l));
		}

		return sb.toString().trim();
	}

	private String buildLocation(Location l) {
		StringBuffer sb = new StringBuffer();

		sb.append("- ");

		sb.append(l.getType()).append(" ");
		if (StringUtils.equals("탐험", l.getType())) {
			sb.append("챕터-").append(l.getValue());
		} else if (StringUtils.equals("어혼던전", l.getType())) {
			sb.append(l.getValue()).append("층");
		} else if (StringUtils.equals("요기봉인", l.getType())) {
			sb.append(l.getValue());
		}

		sb.append(" (").append(l.getCount()).append("마리)").append("\n");

		return sb.toString();
	}
}
