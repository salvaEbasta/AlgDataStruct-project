package spazio_comportamentale;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import comportamentale_fa.ComportamentaleTransition;

public class SpaceStateTransitions {
	
	private SpaceState source;
	private HashMap<ComportamentaleTransition, SpaceState> out;
	
	public SpaceStateTransitions(SpaceState source) {
		this.source = source;
		out = new HashMap<ComportamentaleTransition, SpaceState>();
	}
	
	public SpaceState getSource() {
		return source;
	}
	
	public boolean hasOutputTransitions() {
		return !out.isEmpty();
	}
	
	public HashMap<ComportamentaleTransition, SpaceState> getOutputTransitions(){
		return out;
	}
	
	public ComportamentaleTransition getInputTransition(SpaceState to) {
		if(hasOutputState(to)) {
			for(Entry<ComportamentaleTransition, SpaceState> entry: out.entrySet()) {
				if(entry.getValue().equals(to))
					return entry.getKey();
			}
		}
		return null;
	}
	
	public boolean addOutputTransition(ComportamentaleTransition t, SpaceState s) {
		if(!out.containsKey(t)) {
			out.put(t, s);
			return true;
		}
		return false;
	}
	

	public boolean hasOutputState(SpaceState to) {
		return out.containsValue(to);
	}
	
	@Override
	public boolean equals(Object otherState) {
		SpaceStateTransitions other = (SpaceStateTransitions) otherState;
		return this.source.equals(other.source);
		
	}

	public boolean removeOutputState(SpaceState toRemove) {
		if(hasOutputState(toRemove)) {
			Set<Entry<ComportamentaleTransition, SpaceState>> entries = new HashSet<Entry<ComportamentaleTransition, SpaceState>>(out.entrySet()); //evitare ConcurrentModificationException
			for(Entry<ComportamentaleTransition, SpaceState> entry: entries) {
				if(entry.getValue().equals(toRemove))
					out.remove(entry.getKey());
			}
		}
		return false;
	}
	

}
