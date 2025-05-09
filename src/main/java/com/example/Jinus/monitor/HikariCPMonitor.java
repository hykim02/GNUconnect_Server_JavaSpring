package com.example.Jinus.monitor;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HikariCPMonitor {

    private final HikariDataSource dataSource;

    @Autowired
    public HikariCPMonitor(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Scheduled(fixedRateString = "${monitor.pool.status.rate.ms:60000}") // 상태 출력 주기 (기본 1분)
    public void logHikariStatus() {
        if (dataSource != null) {
            HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();

            int total = poolMXBean.getTotalConnections();
            int active = poolMXBean.getActiveConnections();
            int idle = poolMXBean.getIdleConnections();
            int waiting = poolMXBean.getThreadsAwaitingConnection();

            log.info("[HikariCP 상태] 전체: {} / 사용 중: {} / 유휴: {} / 대기 중: {}",
                    +                    total, active, idle, waiting);
        }
    }
}
