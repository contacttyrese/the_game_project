package com.domain.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
//		UserDao userDao = new UserDao();
		
		// Attempting to retrieve all users then print out each user
//		List<String> users = userDao.getAllUsersOfUserTypeFromUserDirectory("admin");
//		for (String user : users) {
//			System.out.println(user);
//		}
		
		
		//Attempting to use delete object method
		//New BufferedWriter object causes file's contents to be removed
//		User user = new Member(2, "Fred", "Password");
//		userDao.persistObject(user);
	}

}
