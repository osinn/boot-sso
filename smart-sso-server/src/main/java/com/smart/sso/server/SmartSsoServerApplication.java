package com.smart.sso.server;

import com.smart.sso.client.annotation.EnableSSOLocal;
import com.smart.sso.client.annotation.EnableSSORedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableSSOLocal
@EnableSSORedis
@SpringBootApplication
public class SmartSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSsoServerApplication.class, args);
    }
}
