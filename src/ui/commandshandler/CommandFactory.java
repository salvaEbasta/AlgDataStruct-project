package ui.commandshandler;

import ui.commands.base.LoadNet;
import ui.commands.base.NewNet;
import ui.commands.base.ShowNet;
import ui.commands.base.SpaceComp;
import ui.commands.general.Back;
import ui.commands.general.Exit;
import ui.commands.newcfa.NewState;
import ui.commands.newcfa.NewTransition;
import ui.commands.newcfa.ResetCFA;
import ui.commands.newcfa.SaveCFA;
import ui.commands.newcfa.SetInitial;
import ui.commands.newcfa.ShowStates;
import ui.commands.newcfa.ShowTransitions;
import ui.commands.newnet.LinkCFAs;
import ui.commands.newnet.NewCFA;
import ui.commands.newnet.NewEvent;
import ui.commands.newnet.NewLink;
import ui.commands.newnet.ResetNet;
import ui.commands.newnet.SaveNet;
import ui.commands.newnet.ShowCFAs;
import ui.commands.newnet.ShowEvents;
import ui.commands.newnet.ShowLinks;
import ui.commands.spacecomp.GenerateSpace;
import ui.commands.spacecomp.GenerateSpaceObs;
import ui.commands.spacecomp.ShowObservations;
import ui.commands.spacecomp.UpdateNet;

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
	
	public Command updateNet() {
		return new Command(CommandDescription.UPDATENET, new UpdateNet());
	}
	
	public Command spaceComp() {
		return new Command(CommandDescription.SPACECOMP, new SpaceComp());
	}
	
	public Command generateSpace() {
		return new Command(CommandDescription.GENERATESPACE, new GenerateSpace());
	}
	
	public Command generateSpaceObs() {
		return new Command(CommandDescription.GENERATESPACEOBS, new GenerateSpaceObs());
	}
	
	public Command showObservations() {
		return new Command(CommandDescription.SHOWOBS, new ShowObservations());
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
	
	public Command resetNet() {
		return new Command(CommandDescription.RESETNET, new ResetNet());
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
	
	public Command resetCFA() {
		return new Command(CommandDescription.RESETCFA, new ResetCFA());
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
