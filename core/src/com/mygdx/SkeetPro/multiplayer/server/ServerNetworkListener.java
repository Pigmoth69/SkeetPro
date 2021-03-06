package com.mygdx.SkeetPro.multiplayer.server;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.mygdx.SkeetPro.multiplayer.Packets;
import com.mygdx.SkeetPro.screens.GUIMultiplayerMenu;


public class ServerNetworkListener extends Listener{
	public static ArrayList<Connection> clients = new ArrayList<Connection>();
	
	
	@Override
	public void connected(Connection connection) {
		Log.info("[SERVER]Someone has connect!");
		clients.add(connection);
		
		System.out.println("COnnectiones: "+ clients.size());
		
		if(clients.size()>2)
			connection.close();
		
	}

	@Override
	public void disconnected(Connection connection) {
		Log.info("[SERVER]Someone has disconnect!");
		clients.remove(connection);
		
	}
	
	
	//Client to server ou Server to client??? O.o
	//object is the thing received from the client
	//remover os override se der asneira
	@Override
	public void received(Connection connection, Object object) {

		if(object instanceof Packets.PacketClientLogin){
			connection.sendTCP(new Packets.PacketClientLogin());
		}

		if(object instanceof Packets.PacketStartGame){
			//if(clients.size()!=2)
				connection.sendTCP(new Packets.PacketStartGame());
		}
		if(object instanceof Packets.PacketPlayerLost){
			if(connection.equals(clients.get(0))){
				connection.sendTCP(new Packets.PacketPlayerLost());			
				clients.get(1).sendTCP(new Packets.PacketPlayerWon());
			}
			else{
				connection.sendTCP(new Packets.PacketPlayerLost());
				clients.get(0).sendTCP(new Packets.PacketPlayerWon());
			}
			
		}
		if(object instanceof Packets.PacketSendDuck){
			
			if(connection.equals(clients.get(0))){
				clients.get(1).sendTCP(new Packets.PacketSendDuck());
			}
			else{
				clients.get(0).sendTCP(new Packets.PacketSendDuck());
			}
		}
		if(object instanceof Packets.PacketCanPlay){
			System.out.println("Estou a alteraar!!");
			Packets.PacketCanPlay p = new Packets.PacketCanPlay();
			p.num=1;
			connection.sendTCP(p);
		}
		if(object instanceof Packets.PakcetResetConnection){
			resetConnections();
		}
		
	}
	
	public static void resetConnections(){
		for(int i = 0; i < clients.size();i++){
			clients.get(i).close();
		}
	}
}
