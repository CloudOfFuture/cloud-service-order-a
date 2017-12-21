package com.kunlun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CloudServiceOrderAApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudServiceOrderAApplication.class, args);
	}
}
