package ui;

import java.io.IOException;
import java.util.ArrayList;

import ui.stream.SimpleStreamAdapter;
import utility.Constants;


/**
 * Classe contente il punto di partenza da cui far iniziare il programam
 * @author Matteo Salvalai [715827], Lorenzo Maestrini[715780], Jacopo Mora [715149]
 *
 */
public class Main {

	private static CommandsHandler handler;
	
	/**
	 * Il punto da cui far iniziare il programma
	 * @param args lista di argomenti da passare
	 */
	public static void main(String[] args) {
		SimpleStreamAdapter ssa = new SimpleStreamAdapter();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {	
			try {
				handler.close();
				ssa.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		System.out.print(Constants.WELCOME);
		handler = new CommandsHandler(ssa);
		do {
			String command = ssa.read(Constants.EMPTY_STRING);
			if(command.startsWith("readcommands ")) {
				String filename = command.replaceFirst("^readcommands ", Constants.EMPTY_STRING);
				try {
					ArrayList<String> commandsList = new ReadCommands().readLines(filename);
					for(String readCommand: commandsList)
						handler.run(readCommand.trim());
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else				
				handler.run(command.trim());
		}while(true);
	}	
}
