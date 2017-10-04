package net.ujacha.onmyojibot.controller;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ujacha.onmyojibot.entity.Shikigami;
import net.ujacha.onmyojibot.repository.ShikigamiRepository;
import net.ujacha.onmyojibot.utils.Loader;

@Controller
@RequestMapping("shikigami")
public class ShikigamiController {

	
	private static final Logger log = LoggerFactory.getLogger(ShikigamiController.class);
	
	@Autowired
	private ShikigamiRepository shikigamiRepository; 
	
	@Autowired
	private Loader loader;
	
	@PostMapping("load")
	public void load() {
		
		File file = new File(getClass().getClassLoader().getResource("onmyoji.xlsx").getFile());
		
		List<Shikigami> list =  loader.loadShikigami(file);
		
		list.forEach(s -> shikigamiRepository.save(s));
		
	}
	
}
