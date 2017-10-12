package net.ujacha.onmyojibot;

import org.junit.Test;

public class KoreanTextTest {

	@Test
	public void test() {
		
		System.out.format("%d\n", 0xac00);
		
		System.out.println('남' - (0xac00 + (28 * 21 * 2) + (28 * 0) + 16));
		
		System.out.format("%c\n", 0xac00 + (28 * 21 * 1) + (28 * 0) + 16);
		
		
		System.out.format("%d\n", (int)'ㄱ');
		System.out.format("%d\n", (int)'ㅎ' - (int)'ㄱ');
		
		for(int i = 0; i <= 29; i++) {
			System.out.format("%c ", 'ㄱ' + i);
		}
		
		System.out.println();
		
		System.out.println(('감' - (0xac00)) / (28 * 21));
		
		System.out.println(('남' - (0xac00)) / (28 * 21));
		
		System.out.println(('담' - (0xac00)) / (28 * 21));

		for(int i = 0; i < 19; i++) {
			System.out.format("%c\n", 0xac00 + (28 * 21 * i) + (28 * 0) + 0);			
		}
		
		
		
	}
	
}
