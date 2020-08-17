package finite_state_automata;

import java.util.HashSet;
import java.util.Set;

import commoninterfaces.Interconnections;

public class FiniteInterconnections extends Interconnections<FiniteTransition>{
	
	public boolean hasAuto() {
		HashSet<FiniteTransition> tmp = new HashSet<FiniteTransition>(from());
		tmp.retainAll(to());
		return !tmp.isEmpty();
	}
	
	public Set<FiniteTransition> getAuto(){
		HashSet<FiniteTransition> tmp = new HashSet<FiniteTransition>(from());
		tmp.retainAll(to());
		return tmp;
	}

}
