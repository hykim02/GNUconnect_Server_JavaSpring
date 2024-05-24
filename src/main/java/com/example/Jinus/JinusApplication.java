package com.example.Jinus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class JinusApplication {

	private static final Logger logger = LoggerFactory.getLogger(JinusApplication.class);

	public static void main(String[] args) {
		logger.info("Starting application");
		SpringApplication.run(JinusApplication.class, args);
	}

	// Timezone 설정
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));		// 한국은 "Asia/Seoul"
	}
}
