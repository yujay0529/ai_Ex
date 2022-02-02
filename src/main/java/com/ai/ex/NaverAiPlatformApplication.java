package com.ai.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//import com.ai.ex.controller.APIController;

@SpringBootApplication
//@ComponentScan(basePackageClasses=APIController.class)
@ComponentScan(basePackages = {"com.ai.ex"})
public class NaverAiPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaverAiPlatformApplication.class, args);
	}

}
