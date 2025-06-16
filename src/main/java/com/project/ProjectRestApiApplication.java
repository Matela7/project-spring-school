package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableJpaRepositories
public class ProjectRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectRestApiApplication.class, args);
	}

	@Bean
	public RestClient restClient() {
		return RestClient.builder()
				.baseUrl("http://localhost:8081")
				.build();
	}
}