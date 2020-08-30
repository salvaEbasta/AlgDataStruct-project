package diagnosticatore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import fsm_interfaces.StateInterface;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceState;
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
	
	public SilentClosure(SilentClosure sc) {
		super(sc);
		this.accepting = sc.accepting;
		this.decorations = new HashMap<>();
		sc.decorations.forEach((k,v)->this.decorations.put(k, v));
		this.exiting = new HashSet<>(sc.exiting);
	}
	
	public boolean insert(SpaceState s) {
		if(super.insert(s)) {
			if(s.isFinal()) {
				decorations.put(s, Constants.EPSILON);
				accepting = true;
			}
			return true;
		}
		return false;
	}
	
	public boolean remove(SpaceState s) {
		if(super.remove(s)) {
			exiting.remove(s);
			decorations.remove(s);
			accepting = false;
			for(SpaceState state:decorations.keySet())
				if(state.isFinal()) {
					accepting = true;
					break;
				}
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
	
	public String decorationOf(SpaceState s) {
		return decorations.get(s);
	}
	
	public String diagnosis() {
		StringBuilder sb = new StringBuilder();
		decorations.entrySet().forEach(e->{
			if(e.getKey().isFinal())
				sb.append(e.getValue()+"|");
		});
		if(sb.length() > 0)
			sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public Set<SpaceState> exitStates(){
		return exiting;
	}
	
	@Override
	public Set<SpaceState> acceptingStates(){
		return new HashSet<SpaceState>(decorations.keySet());
	}
	
	@Override
	public boolean isAccepting() {
		return accepting;
	}
	
	@Override
	public Object clone() {
		SilentClosure deepCopy = new SilentClosure(this);
		return deepCopy;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !SilentClosure.class.isAssignableFrom(obj.getClass()))
			return false;
		final SilentClosure tmp = (SilentClosure) obj;
		return id().equals(tmp.id());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString()).append("\n");
		sb.append("Decorations:");
		decorations.forEach((k,v)->sb.append("\n\t- "+k.id()+": "+v));
		sb.append("\nExiting States: "+exiting.toString());
		sb.append("\n");
		return sb.toString();
	}
}
