package com.example.Jinus.monitor;

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
public class TomcatThreadMonitor {

    private final ServletWebServerApplicationContext context;

    public TomcatThreadMonitor(ServletWebServerApplicationContext context) {
        this.context = context;
    }

    @Scheduled(fixedRate = 1000) // 10초마다 출력
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

                    System.out.printf(
                            "[Tomcat 스레드] MaxPoolSize: %d, PoolSize: %d, 활성: %d, TaskCount: %d, 완료: %d%n",
                            max, poolSize, active, taskCount, completedTaskCount
                    );
                }
            }
        }
    }
}
