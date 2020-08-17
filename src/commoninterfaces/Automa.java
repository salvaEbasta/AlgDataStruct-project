package commoninterfaces;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Automa<T extends Transition, I extends Interconnections<T>> implements AutomaInterface<T, I>{

	private String id;
	private HashMap<State, I> structure;
	private State initial;
	private State current;
	
	public Automa(String id) {
		this.id = id;
		structure = new HashMap<State, I>();
		initial = null;
		current = null;
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
	public Set<State> states() {
		return new HashSet<State>(structure.keySet());
	}
	
	@Override
	public HashMap<State, I> structure() {
		return structure;
	}
	
	@Override
	public State initialState() {
		return initial;
	}
	
	@Override
	public State currentState() {
		return current;
	}
	
	@Override
	public Set<T> to(State s) {
		if (structure.containsKey(s))
			return structure.get(s).to();
		else
			return new HashSet<T>();
	}
	
	@Override
	public boolean transitionTo(Transition t) {
		if (structure.containsKey(t.destination()) && current.equals(t.source())) {
			current = t.destination();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean setCurrent(State s) {
		if (structure.containsKey(s)) {
			current = s;
			return true;
		}
		return false;
	}
	
	@Override
	public Set<T> from(State s) {
		if (structure.containsKey(s))
			return structure.get(s).from();
		else
			return new HashSet<T>();
	}
	
	@Override
	public boolean add(T t) {
		structure.get(t.source()).from().add(t);
		structure.get(t.destination()).to().add(t);
		return true;
	}
	
	@Override
	public boolean insert(State s) {
		if(!structure.containsKey(s)) {
			structure.put(s, newI());
			return true;
		}
		return false;
	}
	
	private I newI() {
		I i = null;
		try {
			ParameterizedType pt = (ParameterizedType)this.getClass().getDeclaredField("structure").getGenericType();
			i = (I) pt.getActualTypeArguments()[1].getClass().newInstance();
		} catch (NoSuchFieldException | SecurityException | InstantiationException | IllegalAccessException e) {
			i = (I) new Interconnections<T>();
		}
		return i;	
	}
	
	@Override
	public boolean setInitial(State s) {
		initial = s;
		return true;
	}
	
	@Override
	public boolean remove(T t) {
		structure.get(t.source()).from().remove(t);
		structure.get(t.destination()).to().remove(t);
		return true;
	}
	
	@Override
	public boolean remove(State s) {
		if((initial!=null && initial.equals(s))||(current!=null && current.equals(s)))
			return false;
		structure.get(s).from().forEach(t->structure.get(t.destination()).to().remove(t));
		structure.get(s).to().forEach(t->structure.get(t.source()).from().remove(t));
		structure.remove(s);
		return true;
	}
	
	@Override
	public String toString() {
		return structure.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		Automa<T, I> automa = (Automa<T, I>) obj;
		return this.id.equals(automa.id);
	}

}
