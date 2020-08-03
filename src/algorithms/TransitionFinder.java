package algorithms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import finiteStateAutomata.FiniteStateMachine;
import finiteStateAutomata.State;
import finiteStateAutomata.Transition;

public class TransitionFinder {
	
	/**
	 * Trova una sequenza di transizioni di dimensione >=2 dove ogni stato intermedio 
	 * non ha altre transizioni entranti e uscenti al di fuori di quelle che compongono la sequenza
	 * @param N
	 * @return
	 */
	public static List<Transition> oneWayPath(FiniteStateMachine N){
		LinkedList<Transition> sequence = new LinkedList<Transition>();
		for(State s : N.states()) {
			buildSequence(N,s,sequence);
			if(sequence.size() > 1)
				break;
			else
				sequence = new LinkedList<Transition>();
		}
		return sequence;
	}
	
	private static void buildSequence(FiniteStateMachine N, State s, LinkedList<Transition> sequence) {
		if(N.to(s).size() == 1 && N.from(s).size() == 1) {
			Transition in = N.to(s).iterator().next();
			if(!in.isAuto()) {
				sequence.addFirst(in);
				buildUpstream(N, in.source(), sequence);
			}
			Transition out = N.from(s).iterator().next();
			if(!out.isAuto()) {
				sequence.addLast(out);
				buildDownstream(N, out.sink(), sequence);
			}
		}
		else if(N.from(s).size() == 1) {
			Transition out = N.from(s).iterator().next();
			if(!out.isAuto()) {
				sequence.addLast(out);
				buildDownstream(N, out.sink(), sequence);
			}
		}
		else if(N.to(s).size() == 1) {
			Transition in = N.to(s).iterator().next();
			if(!in.isAuto()) {
				sequence.addFirst(in);
				buildUpstream(N, in.source(), sequence);
			}
		}
	}
	
	private static void buildUpstream(FiniteStateMachine N, State source, LinkedList<Transition> sequence) {
		if(N.to(source).size() == 1) {
			Transition in = N.to(source).iterator().next();
			if(!in.isAuto()) {
				sequence.addFirst(in);
				buildUpstream(N, in.source(), sequence);
			}
		}
	}
	
	private static void buildDownstream(FiniteStateMachine N, State sink, LinkedList<Transition> sequence) {
		if(N.from(sink).size() == 1) {
			Transition out = N.from(sink).iterator().next();
			if(!out.isAuto()) {
				sequence.addLast(out);
				buildDownstream(N, out.sink(), sequence);
			}
		}
	}
	
	public static List<Transition> parallelTransitions(FiniteStateMachine N){
		List<Transition> parallels = new LinkedList<Transition>();
		for(State s : N.states()) {
			if(findParallels(N.to(s), parallels).size() > 1 || findParallels(N.from(s), parallels).size() > 1)
				break;
		}
		return parallels;
	}
	
	public static List<Transition> findParallels(Set<Transition> transitions, List<Transition> parallels){
		parallels.clear();
		Iterator<Transition> iter = transitions.iterator();
		while(iter.hasNext()) {
			Transition t = iter.next();
			parallels.add(t);
			List<Transition> tmp = new LinkedList<Transition>(transitions);
			for(Transition t1:tmp)
				if(t1.isParallel(t))
					parallels.add(t1);
			if(parallels.size() > 1)
				break;
			else
				parallels.clear();
		}
		return parallels;
	}
}
