package com.jt.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"jtpf.dynaquery","com.jt.www"})
@EnableTransactionManagement
@EnableCaching
@EnableAsync
@MapperScan(basePackages = "com.jt.www.dao.mapper")
public class CtiApplication {

	@Bean(name = "restTemplate")
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args){
		SpringApplication.run(CtiApplication.class, args);
	}
}

