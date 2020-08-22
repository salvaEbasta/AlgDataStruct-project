package diagnosticatore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import commoninterfaces.StateInterface;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpaceTransition;
import utility.Constants;

/**
 * Descrive una chiusura silenziosa. Si comporta come uno spazio comportamentale
 * per tutti gli stati e le transizioni interne alla chiusura. Possiedono, inoltre, per
 * ogni stato finale|d'uscita una decorazione
 * @author Matteo
 *
 */
public class SilentClosure extends SpaceAutomaComportamentale implements StateInterface{
	private static final long serialVersionUID = -5172582068115487212L;
	private HashMap<SpaceState, String> decorations;
	private HashSet<SpaceState> exiting;
	private boolean accepting;
	
	public SilentClosure(String id) {
		super(id);
		decorations = new HashMap<SpaceState, String>();
		exiting = new HashSet<SpaceState>();
		accepting = false;
	}
	
	public boolean insert(SpaceState s) {
		if(super.insert(s)) {
			if(s.isFinal())
				decorations.put(s, Constants.EPSILON);
			return true;
		}
		return false;
	}
	
	public boolean remove(SpaceState s) {
		if(super.remove(s)) {
			exiting.remove(s);
			decorations.remove(s);
			return true;
		}
		return false;
	}
	
	public void setExiting(SpaceState s) {
		if(super.hasState(s))
			exiting.add(s);
			if(!decorations.containsKey(s)) {
				decorations.put(s, Constants.EPSILON);
			}
	}
	
	public Set<SpaceState> decorableStates(){
		return decorations.keySet();
	}
	
	public boolean decorate(SpaceState s, String decoration) {
		if(decorations.containsKey(s)) {
			decorations.put(s, decoration);
			return true;
		}
		return false;
	}
	
	public String getDecorationOf(SpaceState s) {
		return decorations.get(s);
	}
	
	public String diagnosis() {
		StringBuilder sb = new StringBuilder();
		decorations.entrySet().forEach(e->{
			if(e.getKey().isFinal())
				sb.append(e.getValue()+"|");
		});
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public Set<SpaceState> exitStates(){
		return exiting;
	}

	@Override
	public boolean isAccepting() {
		return accepting;
	}

	@Override
	public void setAccepting(boolean accepting) {
		this.accepting = accepting;
	}
	
	@Override
	public Object clone() {
		SilentClosure deepCopy = (SilentClosure) super.clone();
		deepCopy.accepting = this.accepting;
		deepCopy.decorations = new HashMap<>(this.decorations);
		deepCopy.exiting = new HashSet<>(this.exiting);
		return deepCopy;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("SilentClosure: %s\n", id()));
		sb.append(String.format("[Numero Stati: %d - Numero Transizioni: %d]\n", states().size(), transitions().size()));
		for(SpaceState state: states()) {
			sb.append(state.toString());
			Set<SpaceTransition<SpaceState>>  in = to(state);
			Set<SpaceTransition<SpaceState>> out = from(state);
			if(!in.isEmpty()) {
				sb.append("\n\t- Input Transitions:");
				for(SpaceTransition<SpaceState> inTransition: in) {
					sb.append(String.format("\n\t\t* %s", inTransition));
				}
			}
			if(!out.isEmpty()) {
				sb.append("\n\t- Output Transitions:");
				for(SpaceTransition<SpaceState> outTransition: out) {
					sb.append(String.format("\n\t\t* %s", outTransition));
				}
			}
			sb.append("\n");
		}
		sb.append("Decorations:");
		decorations.forEach((k,v)->sb.append("\n\t- "+k.id()+": "+v));
		sb.append("\nExiting States: "+exiting.toString());
		sb.append("\n");
		return sb.toString();
	}
}
