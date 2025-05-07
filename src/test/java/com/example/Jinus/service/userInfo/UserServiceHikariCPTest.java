package com.example.Jinus.service.userInfo;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceHikariCPTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("멀티스레드 10개 생성 후 id 조회")
    void hikariConnectionTimeoutTest() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int userNo = i;
            executor.submit(() -> {
                try {
                    log.info(">> [{}] 서비스 호출 시작", userNo);
                    userService.selectCampusIdTest("user" + userNo);
                    log.info(">> [{}] 서비스 호출 완료", userNo);
                } catch (Exception e) {
                    log.error(">> [{}] 예외 발생: {}", userNo, e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // 커넥션 풀 상태 모니터링 (2초마다 찍기)
        for (int i = 0; i < 5; i++) {
            printHikariStatus();
            Thread.sleep(2000);
        }

        latch.await();
        executor.shutdown();
    }

    private void printHikariStatus() {
        if (!(dataSource instanceof HikariDataSource hikariDataSource)) {
            log.warn("데이터소스는 Hikari가 아님: {}", dataSource.getClass());
            return;
        }

        HikariPoolMXBean poolStats = hikariDataSource.getHikariPoolMXBean();
        log.info("### HikariCP 상태 체크 -> 총: {}, 사용 중: {}, 유휴: {}, 대기중 쓰레드: {}",
                poolStats.getTotalConnections(),
                poolStats.getActiveConnections(),
                poolStats.getIdleConnections(),
                poolStats.getThreadsAwaitingConnection()
        );
    }
}
