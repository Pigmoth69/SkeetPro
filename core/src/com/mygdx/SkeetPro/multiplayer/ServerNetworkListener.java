package com.mygdx.SkeetPro.multiplayer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;


public class ServerNetworkListener extends Listener{
	
	
	
	@Override
	public void connected(Connection connection) {
		Log.info("[SERVER]Someone has connect!");
		
	}
	
	
	@Override
	public void disconnected(Connection connection) {
		Log.info("[SERVER]Someone has disconnect!");
		
	}
	
	
	//Client to server ou Server to client??? O.o
	//object is the thing received from the client
	//remover os override se der asneira
	@Override
	public void received(Connection connection, Object object) {
		
		if(object instanceof Packets.Packet0LoginRequest){
			Packets.Packet1LoginAwnser loginAwnser = new Packets.Packet1LoginAwnser();
			loginAwnser.accepted = true;
			connection.sendTCP(loginAwnser);
		}
		
		if(object instanceof Packets.Packet1LoginAwnser){
			String message = ((Packets.Packet2Message)object).message;
			Log.info(message);
		}
		
	}
}