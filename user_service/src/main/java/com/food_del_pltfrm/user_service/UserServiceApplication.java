package com.food_del_pltfrm.user_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UserServiceApplication {
	@Value("${jwt.secret.access}")
	private static String jwt_access_key;
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);

		// Получаем значение после создания контекста
		String port = context.getEnvironment().getProperty("jwt.secret.access");
		System.out.println("Jwt Secret Access: " + port);
		//SpringApplication.run(UserServiceApplication.class, args);
	}

}
