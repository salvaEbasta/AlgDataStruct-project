package ui;

import java.util.ArrayList;

/**
 * Classe con il compito di rappresentare i vari stati in cui una lista di comandi si può trovare durante l'esecuzione del programma
 * @author Matteo Salvalai [715827], Lorenzo Maestrini[715780], Jacopo Mora [715149]
 */
public enum CommandsState implements InitCommandsList{

	BASE(){		
		
		/*
		 * (non-Javadoc)
		 * @see main.new InitCommandsList#getCommandsList()
		 */
		@Override
		public ArrayList<Command> getCommandsList(){
			ArrayList<Command> commandsList = new ArrayList<Command>();
			commandsList.add(new CommandFactory().exit());
			commandsList.add(new CommandFactory().newNet());
			//commandsList.add(new CommandFactory().login());
			return commandsList;
		}
	},
	NEWNET(){
		
		/*
		 * (non-Javadoc)
		 * @see main.new InitCommandsList#getCommandsList()
		 */
		@Override
		public ArrayList<Command> getCommandsList(){
			ArrayList<Command> commandsList = new ArrayList<Command>();
			commandsList.add(new CommandFactory().annulla());
			commandsList.add(new CommandFactory().newCFA());
			commandsList.add(new CommandFactory().newLink());
			commandsList.add(new CommandFactory().showCFAs());
			commandsList.add(new CommandFactory().showLinks());
//			commandsList.add(new CommandFactory().logout());
//			commandsList.add(new CommandFactory().modify());
//			commandsList.add(new CommandFactory().newEvent());
//			commandsList.add(new CommandFactory().showWorkInProgress());
//			commandsList.add(new CommandFactory().showNoticeboard());
//			commandsList.add(new CommandFactory().publish());
//			commandsList.add(new CommandFactory().participate());
//			commandsList.add(new CommandFactory().unsubscribe());
//			commandsList.add(new CommandFactory().withdrawProposal());
//			commandsList.add(new CommandFactory().invite());
//			commandsList.add(new CommandFactory().privateSpaceIn());
			return commandsList;
		}
	},
		NEWCFA(){
			
			/*
			 * (non-Javadoc)
			 * @see main.new InitCommandsList#getCommandsList()
			 */
			@Override
			public ArrayList<Command> getCommandsList(){
				ArrayList<Command> commandsList = new ArrayList<Command>();
				commandsList.add(new CommandFactory().annulla());
				commandsList.add(new CommandFactory().newState());
				commandsList.add(new CommandFactory().newEvent());
				commandsList.add(new CommandFactory().newTransition());
				commandsList.add(new CommandFactory().setInitial());
				commandsList.add(new CommandFactory().showStates());
				commandsList.add(new CommandFactory().showEvents());
				commandsList.add(new CommandFactory().showTransitions());
//				commandsList.add(new CommandFactory().showWorkInProgress());
//				commandsList.add(new CommandFactory().showNoticeboard());
//				commandsList.add(new CommandFactory().publish());
//				commandsList.add(new CommandFactory().participate());
//				commandsList.add(new CommandFactory().unsubscribe());
//				commandsList.add(new CommandFactory().withdrawProposal());
//				commandsList.add(new CommandFactory().invite());
//				commandsList.add(new CommandFactory().privateSpaceIn());
				commandsList.add(new CommandFactory().saveCFA());
				return commandsList;
			}
//	},
//	PRIVATESPACE(){
//		/*
//		 * (non-Javadoc)
//		 * @see main.new InitCommandsList#getCommandsList()
//		 */
//		@Override
//		public ArrayList<Command> getCommandsList() {
//			ArrayList<Command> commandsList = new ArrayList<Command>();
//			commandsList.add(new CommandFactory().exit());
//			commandsList.add(new CommandFactory().showNotifications());
//			commandsList.add(new CommandFactory().removeNotifications());
//			commandsList.add(new CommandFactory().privateSpaceOut());
//			commandsList.add(new CommandFactory().showProfile());
//			commandsList.add(new CommandFactory().modifyProfile());
//			return commandsList;
//		}
		
	};
	

}

/**
 * Interfaccia con il compito di restituire una lista di comandi
 * @author Matteo Salvalai [715827], Lorenzo Maestrini[715780], Jacopo Mora [715149]
 *
 */
interface InitCommandsList {	
	
	/**
	 * Restituisce una lista contenente comandi
	 * @return Lista di comandi
	 */
	public ArrayList<Command> getCommandsList();
}
