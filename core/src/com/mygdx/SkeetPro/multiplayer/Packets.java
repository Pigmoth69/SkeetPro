package com.mygdx.SkeetPro.multiplayer;

public class Packets {

	//cliente to the server
	public static class Packet0LoginRequest{};
	
	//server to the client
	public static class Packet1LoginAwnser{boolean accepted = false;};
	
	//cliente to the server
	public static class Packet2Message{String message;};
	
	public static class Packet3KeepAlive{String alive;};
}
