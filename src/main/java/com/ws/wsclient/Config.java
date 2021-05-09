package com.ws.wsclient;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class Config {
    @Bean
    DozerBeanMapper  dozerMapper(){
        return new DozerBeanMapper();
    }

    @Bean
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setRejectedExecutionHandler(generalCallerRunsPolicy());
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(50);
        threadPoolTaskExecutor.setQueueCapacity(0);
        return threadPoolTaskExecutor;
    }

    @Bean
    public ThreadPoolExecutor.CallerRunsPolicy generalCallerRunsPolicy(){
        return new ThreadPoolExecutor.CallerRunsPolicy();
    }

}
