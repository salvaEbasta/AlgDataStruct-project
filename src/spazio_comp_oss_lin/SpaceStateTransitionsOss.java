package spazio_comp_oss_lin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import comportamentale_fa.ComportamentaleTransition;

public class SpaceStateTransitionsOss {
	
	private SpaceStateOss source;
	private HashMap<ComportamentaleTransition, SpaceStateOss> out;
	
	public SpaceStateTransitionsOss(SpaceStateOss source) {
		this.source = source;
		out = new HashMap<ComportamentaleTransition, SpaceStateOss>();
	}
	
	public SpaceStateOss source() {
		return source;
	}
	
	public boolean hasOutputTransitions() {
		return !out.isEmpty();
	}
	
	public HashMap<ComportamentaleTransition, SpaceStateOss> getOutputTransitions(){
		return out;
	}
	
	public ComportamentaleTransition getInputTransition(SpaceStateOss to) {
		if(hasOutputState(to)) {
			for(Entry<ComportamentaleTransition, SpaceStateOss> entry: out.entrySet()) {
				if(entry.getValue().equals(to))
					return entry.getKey();
			}
		}
		return null;
	}
	
	public boolean addOutputTransition(ComportamentaleTransition t, SpaceStateOss s) {
		if(!out.containsKey(t)) {
			out.put(t, s);
			return true;
		}
		return false;
	}
	

	public boolean hasOutputState(SpaceStateOss to) {
		return out.containsValue(to);
	}
	
	@Override
	public boolean equals(Object otherState) {
		SpaceStateTransitionsOss other = (SpaceStateTransitionsOss) otherState;
		return this.source.equals(other.source);
		
	}

	public boolean removeOutputState(SpaceStateOss toRemove) {
		if(hasOutputState(toRemove)) {
			Set<Entry<ComportamentaleTransition, SpaceStateOss>> entries = new HashSet<Entry<ComportamentaleTransition, SpaceStateOss>>(out.entrySet()); //evitare ConcurrentModificationException
			for(Entry<ComportamentaleTransition, SpaceStateOss> entry: entries) {
				if(entry.getValue().equals(toRemove))
					out.remove(entry.getKey());
			}
		}
		return false;
	}
	

}
