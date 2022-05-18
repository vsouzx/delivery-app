package br.com.anhembi.iHealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EntityScan(basePackages ="br.com.anhembi.iHealth.modelo")
@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class IHealthApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(IHealthApplication.class, args);
		
	}
	
	
}
