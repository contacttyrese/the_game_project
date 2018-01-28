package com.domain.game;

public class XboxOneGame extends Game {
	private int gameId;
	private String gameName;
	private String genre;
	
	public XboxOneGame(){
		
	}
	
	public XboxOneGame(String gameName, String genre){
		super(gameName, genre, "Xbox One");
	}
}
