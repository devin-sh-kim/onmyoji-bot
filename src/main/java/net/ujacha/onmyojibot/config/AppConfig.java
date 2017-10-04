package net.ujacha.onmyojibot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.ujacha.onmyojibot.utils.Loader;

@Configuration
public class AppConfig {

	@Bean(name="loader", initMethod="init")
	public Loader loader() {
		return new Loader();
	} 
	
}
