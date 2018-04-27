package com.cangzhitao.jbf.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import com.cangzhitao.jbf.core.listener.JbfApplicationListener;

@SpringBootApplication
@ComponentScan(basePackages={"com.cangzhitao"})
@EnableCaching
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class); 
        app.addListeners(new JbfApplicationListener());
        app.run(args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		SpringApplicationBuilder springApplicationBuilder =  application.sources(Application.class);
		springApplicationBuilder.listeners(new JbfApplicationListener());
		return springApplicationBuilder;
    }

}
