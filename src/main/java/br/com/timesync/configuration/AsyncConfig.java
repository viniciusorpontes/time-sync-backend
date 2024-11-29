package br.com.timesync.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Número de threads mínimas
        executor.setMaxPoolSize(10); // Número máximo de threads
        executor.setQueueCapacity(25); // Tamanho da fila
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }

}