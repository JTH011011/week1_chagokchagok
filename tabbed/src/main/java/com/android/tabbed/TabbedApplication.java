package com.android.tabbed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
// import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class TabbedApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabbedApplication.class, args);
	}


}
