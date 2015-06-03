package com.mygdx.SkeetPro.gamestate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mygdx.SkeetPro.elements.Player;

public class FileSaving {
	
	
	public static void SaveScores(String caminho, Player p1) {
		
		SaveClass g = new SaveClass(p1);
		try
		{
			FileOutputStream fileOut = new FileOutputStream(caminho);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(g);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in " + caminho);
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

	public static boolean LoadGameState(String caminho)
	{
		SaveClass load = null;
		try
		{
			FileInputStream fileIn = new FileInputStream(caminho);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			load = (SaveClass) in.readObject();
			in.close();
			fileIn.close();
			load.LoadSave();
			return true;
		}catch(IOException i)
		{
			System.out.println("ficheiro não encontrado");
			i.printStackTrace();
			return false;
		}catch(ClassNotFoundException c)
		{
			System.out.println("SaveClass class not found");
			c.printStackTrace();
			return false;
		}
		
		
	}


}