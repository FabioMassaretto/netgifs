package br.com.fiap;

import br.com.fiap.service.CategoryService;
import br.com.fiap.service.StorageService;
import br.com.fiap.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NetgifsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetgifsApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserService userService, StorageService storageService) {
		return (args) -> {
			storageService.init();
			userService.startRoles();
		};
	}

}
