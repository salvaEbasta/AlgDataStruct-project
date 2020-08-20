package ui;

import ui.commands.Annulla;
import ui.commands.Back;
import ui.commands.Exit;
import ui.commands.GenerateSpace;
import ui.commands.GenerateSpaceObs;
import ui.commands.LinkCFAs;
import ui.commands.LoadNet;
import ui.commands.NewCFA;
import ui.commands.NewEvent;
import ui.commands.NewLink;
import ui.commands.NewNet;
import ui.commands.NewState;
import ui.commands.NewTransition;
import ui.commands.SaveCFA;
import ui.commands.SaveNet;
import ui.commands.SetInitial;
import ui.commands.ShowCFAs;
import ui.commands.ShowEvents;
import ui.commands.ShowLinks;
import ui.commands.ShowNet;
import ui.commands.ShowStates;
import ui.commands.ShowTransitions;

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
	
	public Command annulla() {
		return new Command(CommandDescription.ANNULLA, new Annulla());
	}
	
	public Command back() {
		return new Command(CommandDescription.BACK, new Back());
	}

	public Command newNet() {
		return new Command(CommandDescription.NEWNET, new NewNet());
	}
	
	public Command loadNet() {
		return new Command(CommandDescription.LOADNET, new LoadNet());
	}
	
	public Command showNet() {
		return new Command(CommandDescription.SHOWNET, new ShowNet());
	}
	
	public Command generateSpace() {
		return new Command(CommandDescription.GENERATESPACE, new GenerateSpace());
	}
	
	public Command generateSpaceObs() {
		return new Command(CommandDescription.GENERATESPACEOBS, new GenerateSpaceObs());
	}
	
	public Command newCFA() {
		return new Command(CommandDescription.NEWCFA, new NewCFA());
	}

	public Command newLink() {
		return new Command(CommandDescription.NEWLINK, new NewLink());
	}
	
	public Command linkCFAs() {
		return new Command(CommandDescription.LINKCFAS, new LinkCFAs());
	}
	
	public Command saveNet() {
		return new Command(CommandDescription.SAVENET, new SaveNet());
	}
	
	public Command newState() {
		return new Command(CommandDescription.NEWSTATE, new NewState());
	}	

	public Command newEvent() {
		return new Command(CommandDescription.NEWEVENT, new NewEvent());
	}
	
	public Command newTransition() {
		return new Command(CommandDescription.NEWTRANSITION, new NewTransition());
	}
	
	public Command setInitial() {
		return new Command(CommandDescription.SETINITIAL, new SetInitial());
	}
	
	public Command saveCFA() {
		return new Command(CommandDescription.SAVECFA, new SaveCFA());
	}	
	
	public Command showCFAs() {
		return new Command(CommandDescription.SHOWCFAS, new ShowCFAs());
	}
	
	public Command showLinks() {
		return new Command(CommandDescription.SHOWLINKS, new ShowLinks());
	}
	
	public Command showStates() {
		return new Command(CommandDescription.SHOWSTATES, new ShowStates());
	}
	
	public Command showEvents() {
		return new Command(CommandDescription.SHOWEVENTS, new ShowEvents());
	}
	
	public Command showTransitions() {
		return new Command(CommandDescription.SHOWTRANSITIONS, new ShowTransitions());
	}

		
	
}
