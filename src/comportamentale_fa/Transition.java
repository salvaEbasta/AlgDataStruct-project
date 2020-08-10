package comportamentale_fa;

import java.util.HashMap;
import comportamentale_fa.labels.OsservableLabel;
import comportamentale_fa.labels.RelevantLabel;

public class Transition {
	
	private String id;
	private State source;
	private State destination;	
	private Event in;
	private Link inputLink;
	private HashMap<Event, Link> out; //Ad ogni evento in uscita è associato un link diverso, andrebbe scelta un'altra struttura dati perchè con l'hashmap
									//è possibile inserire più volte lo stesso "valore" Link
	private OsservableLabel omega;
	private RelevantLabel f;
	
	public Transition(String id, State source, State destination, Event in, Link inputLink, HashMap<Event, Link> out, OsservableLabel omega, RelevantLabel f) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.in = in;
		this.inputLink = inputLink;
		this.out = out;
		this.omega = omega;
		this.f = f;
	}
	
	public Transition(String id, State source, State destination, Event in, Link inputLink, OsservableLabel omega, RelevantLabel f) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.in = in;
		this.inputLink = inputLink;
		this.out = new HashMap<Event, Link>();
		this.omega = omega;
		this.f = f;
	}
	
	public Transition(String id, State source, State destination, HashMap<Event, Link> out, OsservableLabel omega, RelevantLabel f) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.in = null;
		this.inputLink = null;
		this.out = out;
		this.omega = omega;
		this.f = f;
	}

	public String id() {return id;}
	public State source() {return source;}
	public State sink() {return destination;}
	
	public Link getInputLink() {
		return inputLink;
	}
	
	public Event getInputEvent() {
		return in;
	}
	
	public boolean isInputEventEmpty() {
		return in == null;
	}
	
	public boolean isOutputEventsEmpty() {
		return out.isEmpty();
	}
	
	public HashMap<Event, Link> getOutputEvents() {
		return out;
	}
	
	public boolean hasOsservableLabel() {
		return !omega.isEmpty();
	}
	
	public boolean hasRelevantLabel() {
		return !f.isEmpty();
	}
	
	@Override
	public boolean equals(Object otherTransition) {
		Transition other = (Transition) otherTransition;
		return id.equals(other.id) && source.equals(other.source) && destination.equals(other.source) &&
				in.equals(other.in) && out.equals(other.out);		
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(String.format("%s: ", id));
		if(!isInputEventEmpty())
			sb.append(String.format("%s(%s))", in.id(), inputLink.id()));
		if(!isOutputEventsEmpty()) {
			Event[] keySet = new Event[out.size()];
			keySet = out.keySet().toArray(keySet);
			sb.append(String.format("/{%s(%s)", keySet[0].id(), out.get(keySet[0]).id()));
			for(int i=1; i<keySet.length; i++) {
				sb.append(String.format(",%s(%s)", keySet[i], out.get(keySet[i]).id()));
			}
			sb.append("}");			
		}
		sb.append(String.format("[%s - %s]", omega, f)); //creare costante per epsilon
		return sb.toString();
	}

}
