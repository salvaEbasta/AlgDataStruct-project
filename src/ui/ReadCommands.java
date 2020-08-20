package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadCommands{

	public ArrayList<String> readCommands(String path){
		File file = new File(path);
		if(file.exists() && file.canRead()) {			
			try(BufferedReader in = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);){
				String currentLine;
				ArrayList<String> lista = new ArrayList<String>();
				while ((currentLine = in.readLine()) != null) {
					lista.add(currentLine);
				}
				in.close();
				return lista;
			} catch (IOException e) {
				return new ArrayList<String>();
			}
		} else 
			return new ArrayList<String>();		
	}

}