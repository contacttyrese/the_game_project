package com.domain.user;

public class Member extends User {
	private int userId;
	private String username;
	private String password;
	
	public Member(){
		
	}
	
	public Member(int userId, String username, String password){
		super(userId, username, password, "Member");
	}

}
