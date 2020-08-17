package commoninterfaces;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Automa<S extends State, T extends Transition, I extends Interconnections<T>> implements AutomaInterface<S, T, I>{

	private String id;
	private HashMap<S, I> structure;
	private S initial;
	private S current;
	
	public Automa(String id) {
		this.id = id;
		structure = new HashMap<S, I>();
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
	public Set<S> states() {
		return new HashSet<S>(structure.keySet());
	}
	
	@Override
	public HashMap<S, I> structure() {
		return structure;
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
		if (structure.containsKey(t.destination()) && current.equals(t.source())) {
			current = (S) t.destination();
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
		structure.get(t.source()).from().add(t);
		structure.get(t.destination()).to().add(t);
		return true;
	}
	
	@Override
	public boolean insert(S s) {
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
	public boolean setInitial(S s) {
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
	public boolean remove(S s) {
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
		Automa<S, T, I> automa = (Automa<S, T, I>) obj;
		return this.id.equals(automa.id);
	}

}
