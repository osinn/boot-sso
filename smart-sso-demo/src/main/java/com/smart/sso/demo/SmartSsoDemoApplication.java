package com.smart.sso.demo;

import com.smart.sso.client.annotation.EnableSSOLocal;
import com.smart.sso.client.annotation.EnableSSORedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSSOLocal
//@EnableSSORedis
@SpringBootApplication
public class SmartSsoDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartSsoDemoApplication.class, args);
	}
}
