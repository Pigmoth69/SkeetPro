
package com.mygdx.SkeetPro.multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.multiplayer.Network.PlayerLogin;

public class PositionServer {
	Server server;
	HashSet<Character> loggedIn = new HashSet();

	public PositionServer () throws IOException {
		server = new Server() {
			protected Connection newConnection () {
				// By providing our own connection implementation, we can store per
				// connection state without a connection ID to state look up.
				return new CharacterConnection();
			}
		};

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(server);

		server.addListener(new Listener() {
			public void received (Connection c, Object object) {
				// We know all connections for this server are actually CharacterConnections.
				CharacterConnection connection = (CharacterConnection)c;
				Character character = connection.character;

				if (object instanceof PlayerLogin) {
					// Ignore if already logged in.
					if (character != null) return;

					// Reject if the name is invalid.
					String name = ((Login)object).name;


					// Reject if already logged in.
					for (Character other : loggedIn) {
						if (other.name.equals(name)) {
							c.close();
							return;
						}
					}

					character = new Player();
					character.name = register.name;
					character.otherStuff = register.otherStuff;
					character.x = 0;
					character.y = 0;

					loggedIn(connection, character);
					return;
				}


				if (object instanceof MoveCharacter) {
					// Ignore if not logged in.
					if (character == null) return;

					MoveCharacter msg = (MoveCharacter)object;

					// Ignore if invalid move.
					if (Math.abs(msg.x) != 1 && Math.abs(msg.y) != 1) return;

					character.x += msg.x;
					character.y += msg.y;
					if (!saveCharacter(character)) {
						connection.close();
						return;
					}

					UpdateCharacter update = new UpdateCharacter();
					update.id = character.id;
					update.x = character.x;
					update.y = character.y;
					server.sendToAllTCP(update);
					return;
				}
			}


			public void disconnected (Connection c) {
				CharacterConnection connection = (CharacterConnection)c;
				if (connection.character != null) {
					loggedIn.remove(connection.character);

					RemoveCharacter removeCharacter = new RemoveCharacter();
					removeCharacter.id = connection.character.id;
					server.sendToAllTCP(removeCharacter);
				}
			}
		});
		server.bind(Network.port);
		server.start();
	}

	void loggedIn (CharacterConnection c, Character character) {
		c.character = character;

		// Add existing characters to new logged in connection.
		for (Character other : loggedIn) {
			AddCharacter addCharacter = new AddCharacter();
			addCharacter.character = other;
			c.sendTCP(addCharacter);
		}

		loggedIn.add(character);

		// Add logged in character to all connections.
		AddCharacter addCharacter = new AddCharacter();
		addCharacter.character = character;
		server.sendToAllTCP(addCharacter);
	}



	// This holds per connection state.
	static class CharacterConnection extends Connection {
		public Character character;
	}


}
