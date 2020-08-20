package commoninterfaces;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Interconnections<S extends State, T extends Transition<S>> {
	private HashSet<T> in;
	private HashSet<T> out;
	
	public Interconnections() {
		in = new HashSet<T>();
		out = new HashSet<T>();
	}
	
	public boolean newIn(T t) {
		if(!in.contains(t))
			return in.add(t);
		return false;
	}
	
	public boolean addAllIn(Set<T> setIn) {
		int prevSize = in.size();
		for(T t: setIn) {
			if(!in.contains(t))
				in.add(t);
		}
		return in.size() != prevSize;
	}
	
	public boolean newOut(T t) {
		if(!out.contains(t))
			return out.add(t);
		return false;
	}
	
	public boolean addAllOut(Set<T> setOut) {
		int prevSize = out.size();
		for(T t: setOut) {
			if(!out.contains(t))
				out.add(t);
		}
		return out.size() != prevSize;
	}
	
	public boolean remove(T t) {
		return in.remove(t) || out.remove(t);
	} 
	
	public boolean contains(T t) {
		return in.contains(t) || out.contains(t);
	}
	
	public Set<T> to(){
		return in;
	}
	
	public Set<T> from(){
		return out;
	}
	
	public boolean hasAuto() {
		HashSet<T> tmp = new HashSet<T>(from());
		tmp.retainAll(to());
		return !tmp.isEmpty();
	}
	
	public Set<T> getAuto(){
		HashSet<T> tmp = new HashSet<T>(from());
		tmp.retainAll(to());
		return tmp;
	}
	
	public boolean hasObservableEntering() {
		Iterator<T> iter = in.iterator();
		while(iter.hasNext())
			if(!iter.next().isSilent())
				return true;
		return false;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(out.toString());
		sb.deleteCharAt(0);
		sb.insert(0, in.toString().replace("]", ", "));
		return sb.toString();
	}
}
