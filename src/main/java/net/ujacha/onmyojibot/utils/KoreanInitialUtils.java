package net.ujacha.onmyojibot.utils;

public class KoreanInitialUtils {

	private static final char[] INITIAL = {
		'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
	};
	
	public static char initialCharacter(char c) {
		if(c >= '가' && c <= '힣') {
			int i = (c - ('가')) / (28 * 21); 			
			return INITIAL[i];			
		}else {
			return c;
		}
		
	}
	
	public static String initialString(String s) {
		
		char[] cs = s.toCharArray();
		
		StringBuffer sb = new StringBuffer();
		
		for(char c : cs) {
			
			sb.append(initialCharacter(c));
			
		}
		
		return sb.toString();
		
	}
	
}
