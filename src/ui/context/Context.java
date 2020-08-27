package ui.context;

import comportamental_fsm.CFSMnetwork;
import ui.stream.InOutStream;

public class Context {
	
	private InOutStream io;		
	private WorkSpace workspace;
	private CurrentCFA currentCFA;	
	private CurrentNet currentNet;
	
	
	public Context(InOutStream io) {
		this.io = io;
		workspace = new WorkSpace();
	}
	
	public InOutStream getIOStream() {
		return io;
	}
	
	public WorkSpace getWorkSpace() {
		return workspace;
	}
	
	public CurrentNet createNewNet(CFSMnetwork net) {
		currentNet = new CurrentNet(net);
		return currentNet;
	}
	
	public void loadNet(CurrentNet currentNet) {
		this.currentNet = currentNet;
	}
	
	public CurrentNet getCurrentNet() {
		return currentNet;
	}
	
	public void createNewCFA(String id) {
		currentCFA = new CurrentCFA(id);
	}		
	
	public CurrentCFA getCurrentCFA() {
		return currentCFA;
	}
	
	public boolean saveCFA() {
		if(currentCFA != null && currentCFA.isCorrectCFA()) {
			boolean saved = workspace.saveCFA(currentCFA.getCFA());
			if(saved)
				currentCFA = null;
			return saved;		
		} return false;	
	}	
	
	public void reset() {
		currentCFA = null;
		workspace.reset();
	}
}
