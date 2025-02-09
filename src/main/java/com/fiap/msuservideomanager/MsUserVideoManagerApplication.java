package com.fiap.msuservideomanager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "user-video-manager",
				description = "API responsavel pelo gerencimaneto de videos",
				version = "1.0"
		))
@SpringBootApplication
public class MsUserVideoManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUserVideoManagerApplication.class, args);
	}

}
