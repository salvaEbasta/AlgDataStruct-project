package ui.commands.newnet;

import java.io.File;
import java.util.ArrayList;

import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.Link;
import ui.commands.general.CommandInterface;
import ui.commands.general.OneParameter;
import ui.context.Context;
import utility.FileHandler;

public class SaveNet implements CommandInterface, OneParameter{
	
	private static final String PARENT = "Saved Nets/";

	/*
	 * (non-Javadoc)
	 * @see main.commands.strategy.Commands#run(java.lang.String[], main.commands.Context)
	 */
	@Override
	public boolean run(String[] args, Context context) {
		if(!check(args, context))
			return false;
		if(context.getWorkSpace().savedLinksSize() == 0) {
			context.getIOStream().writeln("ERRORE: Creare almeno un link!");
			return false;
		}
		ArrayList<Link> links = new ArrayList<Link>(context.getWorkSpace().getSavedLinks());
		for(Link link: links) {
			if(link.getSource() == null || link.getDestination() == null) {
				context.getIOStream().writeln(String.format("ERRORE: Il link %s non ha un CFA sorgente o di destinazione!", link.id()));
				return false;
			}
		}
		
		CFSMnetwork net = new CFSMnetwork(links);

				
		String fileName = PARENT.concat(args[0]);
		File file = new File(fileName);
		if(file.exists()) {
			String ans = context.getIOStream().yesOrNo("File con nome indicato è già esistente, vuoi sovrascriverlo?");
			if(ans.equalsIgnoreCase("n"));
				fileName = fileName.concat(" (Copy)");
		}
		boolean saved = new FileHandler().save(fileName.concat(".ser"), context.createNewNet(net));
		if(saved) {
			context.getIOStream().writeln(String.format("Rete di CFA salvata correttamente nel percorso %s!", fileName));
			context.getWorkSpace().reset();
			context.setNetFileName(fileName.concat(".ser"));
		}
		else
			context.getIOStream().writeln(String.format("ERRORE: Impossibile salvare la rete CFA sul percorso %s!", fileName));
		return saved;
	}

}
