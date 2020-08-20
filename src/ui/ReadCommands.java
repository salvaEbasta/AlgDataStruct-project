package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadCommands{

	
	public ArrayList<String> readLines(String path) throws IllegalStateException, IOException{
		File file = new File(path);
		if(file.exists() && file.canRead()) {
			BufferedReader in = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
			String currentLine;
			ArrayList<String> lista = new ArrayList<String>();
			while ((currentLine = in.readLine()) != null) {
				lista.add(currentLine);
			}
			in.close();
			return lista;
		} else {
			throw new IllegalStateException("Can't read this file");
		}
		
	}

}