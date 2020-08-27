package spazio_comportamentale;

import java.util.Set;

import algorithm_interfaces.Algorithm;
import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalTransition;

public class SpazioComportamentale extends Algorithm<SpaceAutomaComportamentale>{

	private CFSMnetwork net;
	private SpaceAutomaComportamentale spazioComp;

	public SpazioComportamentale(CFSMnetwork net) {
		this.net = net;
		spazioComp = new SpaceAutomaComportamentale("Spazio Comportamentale");		
	}

	@Override
	public SpaceAutomaComportamentale call() throws Exception {
		if(spazioComp.states().isEmpty()) {
			SpaceState initial = new SpaceState(net.getInitialStates(), net.getActiveEvents());
			spazioComp.insert(initial);
			spazioComp.setInitial(initial);
			buildSpace(initial, net.enabledTransitions()); 
			net.restoreInitial();
			if(Thread.interrupted()) {
				System.out.println("interrotto");
				return spazioComp;
			}
			spazioComp.potatura();
			spazioComp.ridenominazione();
		}
		return spazioComp;
	}

	private void buildSpace(SpaceState state, Set<ComportamentalTransition> enabledTransitions) {
		try {
			Thread.sleep(1550);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
		}
		
		if(enabledTransitions.size()>1) {
			for(ComportamentalTransition transition: enabledTransitions) {
				if(transition.id().equals("b8"))
					System.out.println();
				net.restoreState(state);
				scattoTransizione(state, transition); 
			}		
		} else if (enabledTransitions.size()==1)
			scattoTransizione(state, enabledTransitions.iterator().next());
		else 
			spazioComp.insert(state);
		return;
	}

	private void scattoTransizione(SpaceState source, ComportamentalTransition transition) {
		
		net.transitionTo(transition);	
		SpaceState destination = new SpaceState(net.getCurrentStates(), net.getActiveEvents());
		if(!spazioComp.insert(destination)) {
			SpaceState toSearch = destination;
			destination = spazioComp.states().stream().filter(s -> s.equals(toSearch)).iterator().next();
		}
		SpaceTransition<SpaceState> spaceTransition = new SpaceTransition<SpaceState>(source, destination, transition);
		if(spazioComp.add(spaceTransition))
			buildSpace(destination, net.enabledTransitions());	
		return;
	}

	public SpaceAutomaComportamentale midResult() {	
		SpaceAutomaComportamentale midResult = new SpaceAutomaComportamentale(spazioComp);		
		spazioComp = new SpaceAutomaComportamentale("Spazio Comportamentale");		
		return midResult;
	}

}