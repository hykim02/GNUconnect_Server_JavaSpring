package com.example.Jinus.scheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class HealthCheckScheduler {
    private static final Logger logger = LoggerFactory.getLogger(HealthCheckScheduler.class);

    @Value("${server.port:8080}")
    private int serverPort;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 60000) // 60초마다 실행
    public void performHealthCheck() {
        String url = "http://localhost:" + serverPort + "/actuator/health";
        try {
            restTemplate.getForEntity(url, String.class);
        } catch (Exception e) {
            logger.error("Health check failed: {}", e.getMessage(), e);
        }
    }
}
