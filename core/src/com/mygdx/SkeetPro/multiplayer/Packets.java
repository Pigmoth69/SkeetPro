package com.mygdx.SkeetPro.multiplayer;

public class Packets {

	public static String IP;
	
	
	//cliente to the server
	public static class Packet0LoginRequest{};
	
	//server to the client
	public static class Packet1LoginAwnser{public boolean accepted = false;};
	
	//cliente to the server
	public static class Packet2Message{public String message;};
	
	public static class Packet3KeepAlive{public String alive;}; 
}
