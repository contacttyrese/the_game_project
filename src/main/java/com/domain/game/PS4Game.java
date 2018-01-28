package com.domain.game;

public class PS4Game extends Game {
	private int gameId;
	private String gameName;
	private String genre;
	
	public PS4Game(){
		
	}
	
	public PS4Game(String gameName, String genre){
		super(gameName, genre, "PS4");
	}
}
