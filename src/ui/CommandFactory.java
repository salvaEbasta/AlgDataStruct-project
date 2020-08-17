package ui;


/**
 * Classe con il compito di fornire una nuova istanza di un comando
 * @author Matteo Salvalai [715827], Lorenzo Maestrini[715780], Jacopo Mora [715149]
 *
 */
public class CommandFactory {
	
	/**
	 * Crea il comando per l'uscita dal programma
	 * @return comando exit
	 */
	public Command exit() {
		return new Command(CommandDescription.EXIT, new Exit());
	}
	
	
}
