package com.mygdx.SkeetPro.gamestate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mygdx.SkeetPro.elements.Player;
import com.mygdx.SkeetPro.main.SkeetPro;

public class FileSaving {
	
	
	public static SaveClass SaveScores(String caminho, Player p1) {
		
		//SaveClass g = new SaveClass(p1);
		SkeetPro.SaveState.Save(p1);
		try
		{
			FileOutputStream fileOut = new FileOutputStream(caminho);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(SkeetPro.SaveState);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in " + caminho);
			return SkeetPro.SaveState;
		}catch(IOException i)
		{
			i.printStackTrace();
			return null;
		}
	}

	public static SaveClass LoadGameState(String caminho)
	{
		SaveClass load = null;
		try
		{
			FileInputStream fileIn = new FileInputStream(caminho);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			load = (SaveClass) in.readObject();
			in.close();
			fileIn.close();
			//load.LoadSave();
			return load;
		}catch(IOException i)
		{
			System.out.println("ficheiro não encontrado");
			i.printStackTrace();
			return null;
		}catch(ClassNotFoundException c)
		{
			System.out.println("SaveClass class not found");
			c.printStackTrace();
			return null;
		}
		
		
	}


}