package commoninterfaces;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class Automa<S extends State, T extends Transition<S>> implements FiniteStateMachine<S, T>, Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	protected LinkedHashMap<S, Interconnections<S, T>> structure;
	private S initial;
	private S current;
	
	public Automa(String id) {
		this.id = id;
		structure = new LinkedHashMap<S, Interconnections<S, T>>();
		initial = null;
		current = null;
	}
	
	public Automa(Automa<S, T> automa) {
		this.id = automa.id;
		this.structure = new LinkedHashMap<S, Interconnections<S, T>>();
		automa.structure.entrySet().forEach(e -> this.structure.put(e.getKey(), new Interconnections<S, T>(e.getValue())));
		this.initial = automa.initial;
		this.current = automa.current;
	}
	
	public String id() {
		return id;
	}
	
	@Override
	public Set<T> transitions() {
		HashSet<T> tmp = new HashSet<T>();
		structure.values().forEach(conn->{
			tmp.addAll(conn.to());
			tmp.addAll(conn.from());
		});
		return tmp;
	}
	@Override
	public Set<S> states() {
		return new HashSet<S>(structure.keySet());
	}
	
	@Override
	public S initialState() {
		return initial;
	}
	
	@Override
	public S currentState() {
		return current;
	}
	
	@Override
	public Set<T> to(S s) {
		if (structure.containsKey(s))
			return structure.get(s).to();
		else
			return new HashSet<T>();
	}
	
	@Override
	public boolean transitionTo(T t) {
		if (structure.containsKey(t.sink()) && current.equals(t.source())) {
			current = (S) t.sink();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean setCurrent(S s) {
		if (structure.containsKey(s)) {
			current = s;
			return true;
		}
		return false;
	}
	
	@Override
	public Set<T> from(S s) {
		if (structure.containsKey(s))
			return structure.get(s).from();
		else
			return new HashSet<T>();
	}
	
	@Override
	public boolean add(T t) {
		return structure.get(t.source()).from().add(t) && structure.get(t.sink()).to().add(t);
	}
	
	@Override
	public boolean insert(S s) {
		if(!structure.containsKey(s)) {
			structure.put(s, new Interconnections<S, T>());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean setInitial(S s) {
		initial = s;
		return true;
	}
	
	@Override
	public boolean remove(T t) {
		return structure.get(t.source()).from().remove(t) && structure.get(t.sink()).to().remove(t);
	}
	
	@Override
	public boolean remove(S s) {
		if((initial!=null && initial.equals(s))||(current!=null && current.equals(s)))
			return false;		
		structure.get(s).from().forEach(t->structure.get(t.sink()).to().remove(t));
		structure.get(s).to().forEach(t->structure.get(t.source()).from().remove(t));
		structure.remove(s);
		return true;
	}
	
	@Override
	public boolean hasAuto(S s) {
		return structure.get(s).hasAuto();
	}
	
	@Override
	public T getAuto(S s) {
		return structure.get(s).getAuto().iterator().next();
	}
	
	@Override
	public boolean hasState(S s) {
		return structure.containsKey(s);
	}	

	@Override
	public Set<S> acceptingStates() {
		HashSet<S> tmp = new HashSet<S>();
		structure.keySet().forEach(s->{
			if(s.isAccepting())
				tmp.add(s);
		});
		return tmp;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Automa: %s\n", id()));
		sb.append(String.format("[Numero Stati: %d - Numero Transizioni: %d]\n", states().size(), transitions().size()));
		for(S state: states()) {
			sb.append(state.toString());
			Set<T>  in = to(state);
			Set<T> out = from(state);
			if(!in.isEmpty()) {
				sb.append("\n\t- Input Transitions:");
				for(T inTransition: in) {
					sb.append(String.format("\n\t\t* %s", inTransition));
				}
			}
			if(!out.isEmpty()) {
				sb.append("\n\t- Output Transitions:");
				for(T outTransition: out) {
					sb.append(String.format("\n\t\t* %s", outTransition));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		Automa<S, T> automa = (Automa<S, T>) obj;
		return this.id.equals(automa.id);
	}
	
	@Override
	public Object clone() {
		try {
			Automa<S, T> deepCopy = (Automa<S, T>) super.clone();
			structure.forEach((key, value)->{
				deepCopy.structure.put(key, (Interconnections<S, T>) value.clone());
			});
			deepCopy.initial = this.initial;
			deepCopy.current = this.current;
			return deepCopy;
		}catch(CloneNotSupportedException e) {
			return new Automa<S, T>(this.id);
		}
	}
}
