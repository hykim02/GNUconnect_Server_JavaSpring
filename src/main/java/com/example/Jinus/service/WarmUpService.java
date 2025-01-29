package com.example.Jinus.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "warmup.enabled", havingValue = "true")
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
    @ConditionalOnProperty(name = "warmup.scheduled.enabled", havingValue = "true")
    public void warmUpEndpoints() {
        logger.info("Starting warm-up process...");
        RestTemplate restTemplate = new RestTemplate();
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
        handlerMethods.forEach((mappingInfo, handlerMethod) -> {
            if (mappingInfo.getMethodsCondition() != null) {
                Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
                Set<String> patterns = mappingInfo.getPatternValues();

                patterns.forEach(pattern -> {
                    if (methods.contains(RequestMethod.POST)) {
                        String url = baseUrl + pattern;
                        try {
                            logger.debug("Warming up endpoint: {} {}", RequestMethod.POST, url);
                            // POST 요청을 위한 빈 요청 본문 생성
                            HttpHeaders headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);

                            String requestBody = """
                                    {
                                      "intent": {
                                        "id": "yvm1dtgi7nym54z0aaid71pl",
                                        "name": "블록 이름"
                                      },
                                      "userRequest": {
                                        "timezone": "Asia/Seoul",
                                        "params": {
                                          "ignoreMe": "true"
                                        },
                                        "block": {
                                          "id": "yvm1dtgi7nym54z0aaid71pl",
                                          "name": "블록 이름"
                                        },
                                        "utterance": "발화 내용",
                                        "lang": null,
                                        "user": {
                                          "id": "74f7e7ab2bf19e63bb1ec845b760631259d7615440a2d3db7b344ac48ed1bbcde5",
                                          "type": "accountId",
                                          "properties": {}
                                        }
                                      },
                                      "bot": {
                                        "id": "65e8142d0a73415ce0694943",
                                        "name": "봇 이름"
                                      },
                                      "action": {
                                        "name": "g7e6t8yqcr",
                                        "clientExtra": {
                                          "sys_campus_id": "1"
                                        },
                                        "params": {
                                            "sys_date": "오늘",
                                            "sys_cafeteria_name": "아람관"
                                        },
                                        "id": "yvy3cqsh14brsvm9uf55cktv",
                                        "detailParams": {
                                            "sys_cafeteria_name": {
                                                "origin": "아람관"
                                            }
                                        }
                                      }
                                    }""";

                            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

                            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                            logger.debug("Warm-up response status: {}", response.getStatusCode());
                        } catch (Exception e) {
                            logger.warn("Failed to warm up endpoint: {} - {}", url, e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
