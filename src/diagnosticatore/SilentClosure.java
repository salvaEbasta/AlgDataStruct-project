package diagnosticatore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import fsm_interfaces.StateInterface;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpaceTransition;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpaceStateObs;
import utility.Constants;

/**
 * Descrive una chiusura silenziosa. Si comporta come uno spazio comportamentale
 * per tutti gli stati e le transizioni interne alla chiusura. Possiedono, inoltre, per
 * ogni stato finale|d'uscita una decorazione
 * @author Matteo
 *
 */
public class SilentClosure extends SpaceAutomaObsLin implements StateInterface{
	private static final long serialVersionUID = -5172582068115487212L;
	private HashMap<SpaceStateObs, String> decorations;
	private HashSet<SpaceStateObs> exiting;
	private boolean accepting;
	
	public SilentClosure(String id) {
		super(id);
		decorations = new HashMap<SpaceStateObs, String>();
		exiting = new HashSet<SpaceStateObs>();
		accepting = false;
	}
	
	public SilentClosure(SilentClosure sc) {
		super(sc);
		this.accepting = sc.accepting;
		this.decorations = new HashMap<>();
		sc.decorations.forEach((k,v)->this.decorations.put(k, v));
		this.exiting = new HashSet<>(sc.exiting);
	}
	
	public boolean insert(SpaceStateObs s) {
		if(super.insert(s)) {
			if(s.isFinal()) {
				decorations.put(s, Constants.EPSILON);
				accepting = true;
			}
			return true;
		}
		return false;
	}
	
	public boolean remove(SpaceStateObs s) {
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
	
	public void setExiting(SpaceStateObs s) {
		if(super.hasState(s))
			exiting.add(s);
			if(!decorations.containsKey(s)) {
				decorations.put(s, Constants.EPSILON);
			}
	}
	
	public Set<SpaceStateObs> decorableStates(){
		return decorations.keySet();
	}
	
	public boolean decorate(SpaceStateObs s, String decoration) {
		if(decorations.containsKey(s)) {
			decorations.put(s, decoration);
			return true;
		}
		return false;
	}
	
	public String decorationOf(SpaceStateObs s) {
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
	
	public Set<SpaceStateObs> exitStates(){
		return exiting;
	}
	
	@Override
	public Set<SpaceStateObs> acceptingStates(){
		return new HashSet<SpaceStateObs>(decorations.keySet());
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
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("SilentClosure: %s\n", id()));
		sb.append(String.format("[Numero Stati: %d - Numero Transizioni: %d]\n", states().size(), transitions().size()));
		for(SpaceStateObs state: states()) {
			sb.append(state.toString());
			Set<SpaceTransition<SpaceStateObs>>  in = to(state);
			Set<SpaceTransition<SpaceStateObs>> out = from(state);
			if(!in.isEmpty()) {
				sb.append("\n\t- Input Transitions:");
				for(SpaceTransition<SpaceStateObs> inTransition: in) {
					sb.append(String.format("\n\t\t* %s", inTransition));
				}
			}
			if(!out.isEmpty()) {
				sb.append("\n\t- Output Transitions:");
				for(SpaceTransition<SpaceStateObs> outTransition: out) {
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
