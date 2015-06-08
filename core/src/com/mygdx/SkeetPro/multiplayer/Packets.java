package com.mygdx.SkeetPro.multiplayer;

public class Packets {

	public static String IP;

	public static class PacketClientLogin{};
	public static class PacketSendDuck{};
	public static class PacketPlayerLost{};
	public static class PacketKeepAlive{};
	public static class PacketStartGame{};
	public static class PacketPlayerWon{};
	public static class PacketCanPlay{public int num;};
	public static class PakcetResetConnection{};
	
}
