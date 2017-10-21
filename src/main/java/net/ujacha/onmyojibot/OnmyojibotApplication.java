package net.ujacha.onmyojibot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class OnmyojibotApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OnmyojibotApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(OnmyojibotApplication.class, args);
	}
}
