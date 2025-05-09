package com.example.Jinus.monitor;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HikariCPMonitor {

    private final HikariDataSource dataSource;

    @Autowired
    public HikariCPMonitor(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Scheduled(fixedRate = 1000) // 10초마다 상태 출력
    public void logHikariStatus() {
        if (dataSource != null) {
            HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();

            int total = poolMXBean.getTotalConnections();
            int active = poolMXBean.getActiveConnections();
            int idle = poolMXBean.getIdleConnections();
            int waiting = poolMXBean.getThreadsAwaitingConnection();

            System.out.printf(
                    "[HikariCP 상태] 전체: %d / 사용 중: %d / 유휴: %d / 대기 중: %d%n",
                    total, active, idle, waiting
            );
        }
    }
}
