package ui.commands;

import ui.Context;

/**
 * Questa interfaccia viene implementata da tutti i comandi che necessitano di un parametro
 * @author Matteo Salvalai [715827], Lorenzo Maestrini[715780], Jacopo Mora [715149]
 *
 */
public interface OneParameter {
	
	/**
	 * Controlla che ci sia un solo parametro
	 * @param args gli argomenti forniti al comando
	 * @param ctx il contesto su cui opera
	 * @param errorZero stringa di errore quando ci sono zero parametri
	 * @param errorMultiple stringa di errore quando ci sono troppi parametri 
	 * @return True - se c'è un solo parametro<br>False - se non c'è un singolo parametro
	 */
	public default boolean check(String[] args, Context context) {
		if(args.length == 0) {
			context.getIOStream().writeln("Inserire un parametro");
			return false;
		} else if(args.length > 1) {
			context.getIOStream().writeln("Inserire un solo parametro");
			return false;
		}
		return true;
	}
}
