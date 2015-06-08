package com.mygdx.SkeetPro.multiplayer.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.mygdx.SkeetPro.main.SkeetPro;
import com.mygdx.SkeetPro.multiplayer.Packets;
import com.mygdx.SkeetPro.multiplayer.server.ServerNetworkListener;
import com.mygdx.SkeetPro.screens.GUIMultiplayerGame;
import com.mygdx.SkeetPro.screens.GUIMultiplayerMenu;
import com.mygdx.SkeetPro.screens.GUIResult;
import com.mygdx.SkeetPro.screens.GUIScreen;

public class ClientNetworkListener extends Listener{
	public static Client client;
	
	
	public void init(Client client) {
		this.client=client;
		
	}
	
	
	@Override
	public void connected(Connection connection) {
		Log.info("[CLIENT]You have connected");
		client.sendTCP(new Packets.PacketClientLogin());
		
		
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
		
		if(object instanceof Packets.PacketStartGame){
			GUIScreen.game.switchTo(SkeetPro.State.MULTIPLAYER_WAITING);
		}
		if(object instanceof Packets.PacketPlayerLost){
			GUIResult.resultText = "YOU WON :)";
			GUIScreen.game.switchTo(SkeetPro.State.RESULT);
		}
		if(object instanceof Packets.PacketPlayerWon){
			GUIResult.resultText = "YOU LOST :(";
			GUIScreen.game.switchTo(SkeetPro.State.RESULT);
			
		}
		if(object instanceof Packets.PacketSendDuck){
			for(int i = 0; i < 50;i++)
				GUIMultiplayerGame.gamestate.createDucks(0);
		}

		/*if(object instanceof Packets.Packet1LoginAwnser){
			boolean awnser = ((Packets.Packet1LoginAwnser)object).accepted;
			*/
			/*if(awnser){
				Log.info("Please enter you first message to the server!");*/
				/*while(true){
					if(GameClient.scanner.hasNext()){
						Packets.Packet2Message mpacket = new Packets.Packet2Message();
						mpacket.message = GameClient.scanner.nextLine();
						client.sendTCP(mpacket);
						Log.info("Please enter another message!");
					}*/
				//}
			/*}else{
				connection.close();
			}*/
		}
		
	


	
}
