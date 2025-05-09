package com.example.Jinus.monitor;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.AbstractProtocol;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
@Slf4j
public class TomcatThreadMonitor {

    private final ServletWebServerApplicationContext context;

    public TomcatThreadMonitor(ServletWebServerApplicationContext context) {
        this.context = context;
    }

    @Scheduled(fixedRateString = "${monitor.pool.status.rate.ms:60000}") // 상태 출력 주기 (기본 1분)
    public void logTomcatThreadPoolStatus() {
        if (context.getWebServer() instanceof TomcatWebServer tomcatWebServer) {
            Connector connector = tomcatWebServer.getTomcat().getConnector();
            ProtocolHandler handler = connector.getProtocolHandler();

            if (handler instanceof AbstractProtocol<?> protocol) {
                Executor executor = protocol.getExecutor();

                if (executor instanceof ThreadPoolExecutor threadPoolExecutor) {
                    int max = threadPoolExecutor.getMaximumPoolSize();
                    int poolSize = threadPoolExecutor.getPoolSize();
                    int active = threadPoolExecutor.getActiveCount();
                    long taskCount = threadPoolExecutor.getTaskCount();
                    long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();

                    log.info("[Tomcat 스레드] MaxPoolSize: {}, PoolSize: {}, 활성: {}, TaskCount: {}, 완료: {}",
                            + max, poolSize, active, taskCount, completedTaskCount);
                }
            }
        }
    }
}
