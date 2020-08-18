package spazio_comp_oss_lin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import comportamentale_fa.ComportamentaleFA;
import comportamentale_fa.ComportamentaleFANet;
import comportamentale_fa.ComportamentaleTransition;
import comportamentale_fa.labels.ObservableLabel;

public class SpazioComportamentaleOss {
	
	private ComportamentaleFANet net;
	private SpaceInterconnectionsOss states;
	
	public SpazioComportamentaleOss(ComportamentaleFANet net) {
		this.net = net;
		states = new SpaceInterconnectionsOss();		
	}
	
	public SpaceInterconnectionsOss generaSpazioOsservazione(ObservableLabel[] observation) {
		if(states.isEmpty()) {
			int index = 0;
			SpaceStateOss initial = new SpaceStateOss(Integer.toString(states.size()), net.getInitialStates(), net.getActiveEvents(), index, observation.length);
			states.add(initial);
			buildSpace(initial,  enabledTransitions(observation, index), observation, index);
			net.restoreState(initial);
		}
		return states;
	}
	
	private void buildSpace(SpaceStateOss state, Set<ComportamentaleTransition> enabledTransitions, ObservableLabel[] observation, int index) {
		if(enabledTransitions.size()>1) {
			for(ComportamentaleTransition transition: enabledTransitions) {
				net.restoreState(state);
				scattoTransizione(state, transition, observation, index); 
			}		
		} else if (enabledTransitions.size()==1)
			scattoTransizione(state, enabledTransitions.iterator().next(), observation, index);
		else 
			states.add(state);
	}
	
	private void scattoTransizione(SpaceStateOss source, ComportamentaleTransition transition, ObservableLabel[] observation, int index) {
		net.transitionTo(transition);
		if(index<observation.length && transition.getObservableLabel().equals(observation[index]))
			index++;
		SpaceStateOss next = new SpaceStateOss(Integer.toString(states.size()), net.getActualStates(), net.getActiveEvents(), index, observation.length);
		if(!states.containsKey(next))
			states.add(next);
		else 
			next = states.getState(next);
		if(states.addOutputTransition(source, transition, next))
			buildSpace(next, enabledTransitions(observation, index), observation, index);	
	}
	
	private Set<ComportamentaleTransition> enabledTransitions(ObservableLabel[] observation, int index) {
		Set<ComportamentaleTransition> enabledTransitions = new HashSet<ComportamentaleTransition>();	
		for(ComportamentaleTransition transition: net.enabledTransitions()) {
			if(transition.getObservableLabel().isEmpty())
				enabledTransitions.add(transition);
			else if(index<observation.length && transition.getObservableLabel().equals(observation[index])) {
				enabledTransitions.add(transition);
			}		
		}
		return enabledTransitions;
	}
	
	public boolean potatura() {
		if(!states.isEmpty())
			return states.potatura();
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(SpaceStateTransitionsOss state: states) {
			sb.append(state.getSource().toString());
			HashMap<ComportamentaleTransition, SpaceStateOss>  in = states.getInputTransitions(state.getSource());
			HashMap<ComportamentaleTransition, SpaceStateOss> out = state.getOutputTransitions();
			if(!in.isEmpty()) {
				sb.append("\n\t- Input Transitions:");
				for(Entry<ComportamentaleTransition, SpaceStateOss> entry: in.entrySet()) {
					sb.append(String.format("\n\t\t* %s (da %s) %s", entry.getKey().id(), entry.getValue(), entry.getKey().labels()));
				}
			}
			if(!out.isEmpty()) {
				sb.append("\n\t- Output Transitions:");
				for(Entry<ComportamentaleTransition, SpaceStateOss> entry: out.entrySet()) {
					sb.append(String.format("\n\t\t* %s (verso %s) %s", entry.getKey().id(), entry.getValue(), entry.getKey().labels()));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
