package net.ujacha.onmyojibot;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.ujacha.onmyojibot.kakao.KakaoRequestBody;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KakaoMessageTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test() throws Exception {
		
		KakaoRequestBody kakaoRequestBody = new KakaoRequestBody();
		kakaoRequestBody.setUserKey("TESTUSER");
		kakaoRequestBody.setType("text");
		kakaoRequestBody.setContent("꽃조개");
		
		
		this.mockMvc.perform(get("/kakao/keyboard")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("시작")));
		this.mockMvc.perform(post("/kakao/message").content(new ObjectMapper().writeValueAsString(kakaoRequestBody)).contentType(MediaType.APPLICATION_JSON_UTF8))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("꽃조개")));
					
	}
	
}
