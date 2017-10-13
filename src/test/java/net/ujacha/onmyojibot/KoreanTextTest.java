package net.ujacha.onmyojibot;

import org.junit.Test;

import net.ujacha.onmyojibot.utils.KoreanInitialUtils;

public class KoreanTextTest {
	
	@Test
	public void test() {
		
		for(int i = 0; i < 19; i++) {
			
			char c = (char) (0xac00 + ((28 * 21 * i) + (28 * 0) + 0));
			
			System.out.format("%c - %c\n", c, KoreanInitialUtils.initialCharacter(c));
		}
		
		System.out.println(KoreanInitialUtils.initialString("강남스타일"));
		
		System.out.println(KoreanInitialUtils.initialString("강남 스타일"));
		
		System.out.println(KoreanInitialUtils.initialString("강남 style"));
				
	}
}
