package net.ujacha.onmyojibot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class OnmyojiBotApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OnmyojiBotApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(OnmyojiBotApplication.class, args);
	}
}
