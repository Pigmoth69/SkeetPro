package com.mygdx.SkeetPro.multiplayer.server;
import java.io.IOException;



import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.SkeetPro.multiplayer.Packets;


public class GameServer{
	private Server server;
	private int tcp_port = 25565;
	private int udp_port = 25567;


	public GameServer(){

	}

	public void startServer(){
		server = new Server();
		registerPackets();
		 
		server.start();
		server.addListener(new ServerNetworkListener());
		
		try {
			server.bind(tcp_port,udp_port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StringBuilder IFCONFIG=new StringBuilder();
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
	                IFCONFIG.append(inetAddress.getHostAddress().toString()+"\n");
	                }

	            }
	        }
	        Packets.IP = IFCONFIG.toString();
	        System.out.println("O PACOTE: "+Packets.IP);
	    } catch (SocketException ex) {
	        ex.printStackTrace();
	    }


		
	}
	 

	private void registerPackets() {
		Kryo kryo = server.getKryo();
		kryo.register(Packets.PacketClientLogin.class);
		kryo.register(Packets.PacketSendDuck.class);
		kryo.register(Packets.PacketPlayerLost.class);
		kryo.register(Packets.PacketStartGame.class);
		kryo.register(Packets.PacketKeepAlive.class);
	}
	
	public void closeServer(){
		server.stop();
	}
	
	public Server getServer(){
		return server;
	}


	
	
	
	
}
