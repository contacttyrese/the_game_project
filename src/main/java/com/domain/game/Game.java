package com.domain.game;

public abstract class Game {
	private static int gameIdCounter = 0;
	private int gameId;
	private String gameName;
	private String genre;
	private String platform;
	
	public Game(){
		
	}
	
	public Game(String gameName, String genre, String platform){
		this.gameId = getGameIdCounter();
		this.gameName = gameName;
		this.genre = genre;
		this.platform = platform;
	}
	
	// Temporarily used to retrieve unique game ID
	private int getGameIdCounter(){
		gameIdCounter++;
		return gameIdCounter;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
}
