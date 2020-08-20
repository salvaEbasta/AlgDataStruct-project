package ui;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ui.commands.NoParameters;
import ui.stream.InOutStream;
import utility.Constants;


/**
 * Classe in grado di gestire un insieme di comandi e di poter fare operazioni su di essi.<br>
 * La classe implementa il design pattern Singleton
 * @author Matteo Salvalai [715827], Lorenzo Maestrini[715780], Jacopo Mora [715149]
 *
 */
public class CommandsHandler implements Closeable{
	/**
	 * Il formato con cui vengono stampati i comandi
	 */
	private static final String TOSTRING_FORMAT = "\n\t%-30s%-65s%-25s";
	/**
	 * Espressione regolare per l'estrazione del comando
	 */
	private static final String REGEX_COMMAND = "^[a-z][A-Za-z]+( )?";

	/**
	 * Contiene i comandi
	 */
	private ArrayList<Command> cList;
	
	private ArrayList<Command> previous;
	
	private Context context;
	
	
	/**
	 * Costruttore
	 * @param IOStream il canale di comunicazione con l'esterno
	 */
	public CommandsHandler(InOutStream IOStream) {
		cList = new ArrayList<Command>();
		this.context = new Context(IOStream);
		cList = CommandsState.BASE.getCommandsList();
	}

	
	/**Restituisce il comando (se presente) che inizia con la stringa passata per parametro
	 * @param initial Stringa iniziale di un comando
	 * @return Nome del comando se trovato <br> la stringa iniziale altrimenti
	 */
	public String hint(String initial) {
		for(Command command: cList) {
			if(command.getName().startsWith(initial))
				return command.getName() + " ";
		}
		return initial;
	}	
	
	/**
	 * Estrae dall'input dell'utente il comando, separandolo dagli eventuali parametri
	 * @param input input dell'utente
	 * @return l'effettivo comando
	 */
	public String getCommand(String input) {
		Matcher matcher = Pattern.compile(REGEX_COMMAND).matcher(input);
		if(matcher.find())
			return matcher.group().trim();
		return Constants.EMPTY_STRING;
	}
	
	/**
	 * Controlla se è presente un comando con il nome inserito 
	 * @param key il nome del comando di cui si vuole verificare la presenza
	 * @return True - è presente un comando con il nome inserito<br>False - non è presente un comando con il nome inserito
	 */
	public boolean contains(String key) {
		String command = getCommand(key);
		if(command != null && command.equals("help")) 
			return true;
		return cList.stream()
				.anyMatch((c)->c.hasName(command));
	}

	/**
	 * Esegue il comando di cui si è inserito il nome, se presente
	 * @param input input dell'utente, contenente il comando e gli eventuali parametri
	 */
	public void run(String input) {
		String command = getCommand(input);
		String parameters = input.replaceAll(command, "").trim();
		String[] args = new String[0];
		if(!parameters.equals("")) {
			args = new String[1];
			args[0] = parameters;
		}
		
		if(command.equals("help"))
			context.getIOStream().writeln(toString());
		else if(command.equals("readcommands")) { 
			String filename = args[0];
			if(filename.matches("[^\\,\\/:*\"<>|]+\\.txt")) {
			ArrayList<String> commandsList = readCommands(filename);
			for(String readCommand: commandsList)
				run(readCommand.trim());
			}
		}
		else if(!contains(command)) 
			context.getIOStream().writeln(Constants.ERROR_UNKNOWN_COMMAND);
		else if(cList.stream()
				.filter((c)->c.hasName(command))
				.findFirst().get()
				.run(args, context)) {
					if(new CommandFactory().newNet().hasName(command)) 
						cList = CommandsState.NEWNET.getCommandsList();					
					else if(new CommandFactory().newCFA().hasName(command)) 
						cList = CommandsState.NEWCFA.getCommandsList();					
					else if(new CommandFactory().annulla().hasName(command))
						cList = CommandsState.BASE.getCommandsList();
					else if(new CommandFactory().back().hasName(command)) {
						if(equalsCommandsList(CommandsState.NEWCFA.getCommandsList()))
							cList = CommandsState.NEWNET.getCommandsList();	
						else if(equalsCommandsList(CommandsState.NEWNET.getCommandsList()))
						cList = CommandsState.BASE.getCommandsList();		
					}
						
			}
		context.getIOStream().write(Constants.NEW_LINE + Constants.WAITING);

	}
	
	private boolean equalsCommandsList(ArrayList<Command> commandsList) {
		commandsList.removeAll(cList);
		return commandsList.isEmpty();
	}
	
	private ArrayList<String> readCommands(String path){
		path = "Commands Blocks/".concat(path);
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
	
	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("I comandi a tua disposizione:");
		sb.append(String.format(TOSTRING_FORMAT, CommandDescription.READCOMMANDS.getName(), CommandDescription.READCOMMANDS.getDescription(), CommandDescription.READCOMMANDS.getSyntax()));
		cList.stream()
			.forEachOrdered((c)->sb.append(String.format(TOSTRING_FORMAT, c.getName(), c.getDescription(), c.getSyntax())));
		return sb.toString();
	}

	/**
	 * Chiude tutte le risorse usate
	 * @throws IOException in caso di errori nella chiusura
	 */
	@Override
	public void close() throws IOException {
		context.getIOStream().close();
	}
}