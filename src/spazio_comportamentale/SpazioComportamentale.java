package spazio_comportamentale;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import comportamentale_fa.ComportamentaleFANet;
import comportamentale_fa.ComportamentaleTransition;

public class SpazioComportamentale {
	
	private ComportamentaleFANet net;
	private SpaceInterconnections states;
	
	public SpazioComportamentale(ComportamentaleFANet net) {
		this.net = net;
		states = new SpaceInterconnections();		
	}
	
	public SpaceInterconnections generaSpazio() {
		if(states.isEmpty()) {
			SpaceState initial = new SpaceState(Integer.toString(states.size()), net.getInitialStates(), net.getActiveEvents());
			states.add(initial);
			buildSpace(initial, net.enabledTransitions());
			net.restoreState(initial);
		}
		return states;
	}
	
	private void buildSpace(SpaceState state, Set<ComportamentaleTransition> enabledTransitions) {
		if(enabledTransitions.size()>1) {
			for(ComportamentaleTransition transition: enabledTransitions) {
				net.restoreState(state);
				scattoTransizione(state, transition); 
			}		
		} else if (enabledTransitions.size()==1)
			scattoTransizione(state, enabledTransitions.iterator().next());
		else 
			states.add(state);
	}
	
	private void scattoTransizione(SpaceState source, ComportamentaleTransition transition) {
		net.transitionTo(transition);	
		SpaceState next = new SpaceState(Integer.toString(states.size()), net.getActualStates(), net.getActiveEvents());
		if(!states.containsKey(next))
			states.add(next);
		else 
			next = states.getState(next);
		if(states.addOutputTransition(source, transition, next))
			buildSpace(next, net.enabledTransitions());	
	}
	
	public boolean potatura() {
		if(states.isEmpty())
			generaSpazio();
		return states.potatura();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(SpaceStateTransitions state: states) {
			sb.append(state.getSource().toString());
			HashMap<ComportamentaleTransition, SpaceState>  in = states.getInputTransitions(state.getSource());
			HashMap<ComportamentaleTransition, SpaceState> out = state.getOutputTransitions();
			if(!in.isEmpty()) {
				sb.append("\n\t- Input Transitions:");
				for(Entry<ComportamentaleTransition, SpaceState> entry: in.entrySet()) {
					sb.append(String.format("\n\t\t* %s (da %s) %s", entry.getKey().id(), entry.getValue(), entry.getKey().labels()));
				}
			}
			if(!out.isEmpty()) {
				sb.append("\n\t- Output Transitions:");
				for(Entry<ComportamentaleTransition, SpaceState> entry: out.entrySet()) {
					sb.append(String.format("\n\t\t* %s (verso %s) %s", entry.getKey().id(), entry.getValue(), entry.getKey().labels()));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
