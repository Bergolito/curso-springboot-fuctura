package br.com.fuctura.leonardo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class LeonardoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeonardoApplication.class, args);
	}

}
