package com.example.Jinus.component;

import com.example.Jinus.controller.NoticeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ProcessMonitor {

    private AtomicBoolean isRequestReceived = new AtomicBoolean(false);
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


//    @Scheduled(fixedDelay = 60000) // 60초마다 실행
//    public void checkRequestReceived() {
//        if (!isRequestReceived.get()) {
//            // 사용자 요청이 없는 경우 프로세스 종료
//            logger.info("request 요청 없음 - 프로세스 종료");
//            System.exit(0);
//        }
//        // 요청 여부 초기화
//        isRequestReceived.set(false);
//    }

    // 요청이 발생할 때마다 호출되는 메서드
    public void onRequestReceived() {
        logger.info("request 요청됨 - 프로세스 실행");
        isRequestReceived.set(true);
    }

    public void startProcessShutdownTimer() {
        // 30초 후에 프로세스 종료 작업 스케줄링
        scheduler.schedule(() -> {
            if (!isRequestReceived.get()) {
                // 사용자 요청이 없는 경우 프로세스 종료
                logger.info("30초 동안 요청이 없음 - 프로세스 종료");
                System.exit(0);
            }
        }, 30, TimeUnit.SECONDS);
    }
}
