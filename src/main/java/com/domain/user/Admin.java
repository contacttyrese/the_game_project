package com.domain.user;

public class Admin extends User {
	private int userId;
	private String username;
	private String password;
	
	public Admin(){
		
	}
	
	public Admin(int userId, String username, String password){
		super(userId, username, password, "Admin");
	}

}
