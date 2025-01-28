package com.example.Jinus.service;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class WarmUpService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WarmUpService.class);
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private boolean initialized = false;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!initialized) {
            logger.info("Application context refreshed. Starting initial warm-up...");
            warmUpEndpoints();
            initialized = true;
        }
    }

    @Scheduled(fixedDelayString = "${warmup.delay:3600000}")
    public void warmUpEndpoints() {
        logger.info("Starting warm-up process...");
        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods =
                requestMappingHandlerMapping.getHandlerMethods();

            logger.info("Found {} endpoints", handlerMethods.size());

            // 모든 등록된 엔드포인트 출력
            handlerMethods.forEach((mappingInfo, handlerMethod) -> {
                logger.info("Found endpoint: {} -> {}#{}",
                    mappingInfo,
                    handlerMethod.getBeanType().getSimpleName(),
                    handlerMethod.getMethod().getName());
            });

            String baseUrl = "https://connectgnu.kro.kr";

            for (RequestMappingInfo mappingInfo : handlerMethods.keySet()) {
                if (mappingInfo.getPatternsCondition() != null) {
                    Set<String> patterns = mappingInfo.getPatternsCondition().getPatterns();
                    Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();

                    for (String pattern : patterns) {
                        for (RequestMethod method : methods) {
                            try {
                                String url = baseUrl + pattern;
                                logger.info("Attempting to warm up: {} {}", method, url);

                                switch (method) {
                                    case GET:
                                        restTemplate.getForObject(url, String.class);
                                        break;
                                    case POST:
                                        restTemplate.postForObject(url, new HashMap<>(), String.class);
                                        break;
                                }

                                logger.info("Successfully warmed up endpoint: {} {}", method, url);
                            } catch (Exception e) {
                                logger.error("Error warming up endpoint: {} {} - {}",
                                    method, pattern, e.getMessage(), e);
                            }
                        }
                    }
                }
            }
            logger.info("Warm-up process completed.");
        } catch (Exception e) {
            logger.error("Error during warm-up process", e);
        }
    }
}
