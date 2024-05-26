package com.juliaosistem.mcs_financial;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McsFinancialApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsFinancialApplication.class, args);
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("springshop-public")
				.packagesToScan("juliaosistem.mcs_financial")
				.build();
	}
}
