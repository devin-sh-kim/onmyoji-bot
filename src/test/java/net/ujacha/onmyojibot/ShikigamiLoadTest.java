package net.ujacha.onmyojibot;

import java.io.File;
import java.util.List;

import net.ujacha.onmyojibot.entity.SecretLetter;
import net.ujacha.onmyojibot.repository.SecretLetterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ujacha.onmyojibot.entity.Shikigami;
import net.ujacha.onmyojibot.repository.ShikigamiRepository;
import net.ujacha.onmyojibot.utils.Loader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShikigamiLoadTest {
	
	private static final Logger log = LoggerFactory.getLogger(ShikigamiLoadTest.class);


	@Autowired
	private Loader loader;
	
	@Autowired
	private ShikigamiRepository shikigamiRepository;

	@Autowired
	private SecretLetterRepository secretLetterRepository;


	@Test
	public void test() {

//		File file = new File(getClass().getClassLoader().getResource("onmyoji.xlsx").getFile());


//		List<Shikigami> list =  loader.loadShikigami(file);
		
//		list.forEach(s -> {
//			log.debug("{}", s);
//			shikigamiRepository.save(s);
//		});


//		loader.init();

		List<Shikigami> findHint = shikigamiRepository.findByHint("관");
		
		findHint.forEach(s -> log.debug("FIND:{}", s));
		
		List<Shikigami> findName = shikigamiRepository.findByName("맹파");
		
		findName.forEach(s -> log.debug("FIND:{}", s));

		List<SecretLetter> findLetters = secretLetterRepository.findByKeyword("부활");

		findLetters.forEach(sl -> log.debug("LETTER : {}", sl));

		
	}
	
	
}
