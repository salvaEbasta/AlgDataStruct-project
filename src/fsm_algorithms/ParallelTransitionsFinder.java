package fsm_algorithms;

import java.util.LinkedList;

import algorithm_interfaces.Algorithm;
import fsm_interfaces.FiniteStateMachine;
import fsm_interfaces.StateInterface;
import fsm_interfaces.Transition;

public class ParallelTransitionsFinder <S extends StateInterface, T extends Transition<S>> extends Algorithm<LinkedList<T>>{
	private FiniteStateMachine<S, T> N;
	private LinkedList<T> sequence;
	
	public ParallelTransitionsFinder(FiniteStateMachine<S, T> N) {
		this.N = N;
		this.sequence = new LinkedList<T>();
	}
	
	@Override
	public LinkedList<T> call() throws Exception {
		LinkedList<T> transitions = new LinkedList<T>(N.transitions());
		while(transitions.size() > 1) {
			T tmp = transitions.pop();
			transitions.forEach(t->{
				if(t.isParallelTo(tmp))
					sequence.add(t);
			});
			if(sequence.size() > 0) {
				sequence.add(tmp);
				break;
			}else
				sequence.clear();
		}
		transitions.clear();
		return sequence;
	}

	@Override
	public LinkedList<T> midResult() {
		return sequence;
	}

}
