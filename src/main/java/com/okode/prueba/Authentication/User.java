package com.okode.prueba.Authentication;

public class User{
	private String userNick;
	private String password;
	private String token;
	
	public User(String nick, String pwd){
		this.userNick = nick;
		this.password = pwd; 
	}

	public String getUserNick(){
		return this.userNick;
	}

	public void setUserNick(String token){
		this.token = token;
	}

	public String getPassword(){
		return this.password;
	}

	public void setPassword(String token){
		this.token = token;
	}

	public String getToken(){
		return this.token;
	}

	public void setToken(String token){
		this.token = token;
	}
}