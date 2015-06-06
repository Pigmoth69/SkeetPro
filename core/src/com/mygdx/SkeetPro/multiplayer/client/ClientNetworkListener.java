package com.mygdx.SkeetPro.multiplayer.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.mygdx.SkeetPro.multiplayer.Packets;
import com.mygdx.SkeetPro.multiplayer.Packets.Packet0LoginRequest;
import com.mygdx.SkeetPro.multiplayer.Packets.Packet1LoginAwnser;
import com.mygdx.SkeetPro.multiplayer.Packets.Packet2Message;

public class ClientNetworkListener extends Listener{
	private static Client client;
	
	
	public void init(Client client) {
		this.client=client;
		
	}
	
	
	@Override
	public void connected(Connection connection) {
		Log.info("[CLIENT]You have connected");
		client.sendTCP(new Packets.Packet0LoginRequest());
		
	}
	
	
	@Override
	public void disconnected(Connection connection) {
		Log.info("[CLIENT]You have disconnected");
		
	}
	
	
	//Client to server ou Server to client??? O.o
	//object is the thing received from the client
	//remover os override se der asneira
	@Override
	public void received(Connection connection, Object object) {
		
		if(object instanceof Packets.Packet1LoginAwnser){
			boolean awnser = ((Packets.Packet1LoginAwnser)object).accepted;
			
			if(awnser){
				Log.info("Please enter you first message to the server!");
				while(true){
					/*if(GameClient.scanner.hasNext()){
						Packets.Packet2Message mpacket = new Packets.Packet2Message();
						mpacket.message = GameClient.scanner.nextLine();
						client.sendTCP(mpacket);
						Log.info("Please enter another message!");
					}*/
				}
			}else{
				connection.close();
			}
		}
		
	}


	
}
