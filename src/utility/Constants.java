package utility;

public class Constants {
	
	public static final String EPSILON = "ε";
	
	public static final String EMPTY_STRING = "";
	public static final String WAITING = "> ";
	public static final String NEW_LINE = "\n";
	
	public static final String INSERT_HELP = "(inserisci 'help' per vedere i comandi a tua disposizione)";
	public static final String ERROR_UNKNOWN_COMMAND = "Il comando inserito non è stato riconosciuto ".concat(INSERT_HELP);	
	public static final String WELCOME = String.format("Benvenuto nel programma!\n%s.\n\n> ", INSERT_HELP);

	public static final String TOO_PARAMETERS = "Troppi parametri";

}
