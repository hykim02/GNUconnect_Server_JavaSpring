package com.example.Jinus.service.v2.cafeteria;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CafeteriaCacheWarmUp implements ApplicationRunner {

    private final CafeteriaServiceV2 cafeteriaServiceV2;

    // 애플리케이션 실행 시 한 번 실행
    @Override
    public void run(ApplicationArguments args) {
        System.out.println("애플리케이션 시작 시 Cache Warming 실행");
        warmUpCache();
    }

    // 7일마다 자동으로 Cache Warming 실행
    @Scheduled(fixedRate = 7 * 24 * 60 * 60 * 1000) // 7일마다 실행 (밀리초 단위)
    public void warmUpCache() {
        System.out.println("주기적인 Cache Warming 실행");
        for (int campusId = 1; campusId < 5; campusId++) {
            cafeteriaServiceV2.getCafeteriaList(campusId);
        }
    }
}
