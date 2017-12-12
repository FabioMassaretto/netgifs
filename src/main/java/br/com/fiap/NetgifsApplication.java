package br.com.fiap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "br.com.fiap.entity" })
@EnableJpaRepositories(basePackages = { "br.com.fiap.repository" })
@ComponentScan(basePackages = {"br.com.fiap.controller"})
public class NetgifsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetgifsApplication.class, args);
	}

}
