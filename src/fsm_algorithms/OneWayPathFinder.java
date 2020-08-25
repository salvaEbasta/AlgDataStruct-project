package fsm_algorithms;

import java.util.LinkedList;

import algorithm_interfaces.Algorithm;
import fsm_interfaces.FiniteStateMachine;
import fsm_interfaces.StateInterface;
import fsm_interfaces.Transition;

public class OneWayPathFinder <S extends StateInterface, T extends Transition<S>> extends Algorithm<LinkedList<T>>{
	private FiniteStateMachine<S, T> N;
	private LinkedList<T> sequence;
	
	public OneWayPathFinder(FiniteStateMachine<S, T> N) {
		this.N = N;
		this.sequence = new LinkedList<T>();
	}
	
	@Override
	public LinkedList<T> call() throws Exception {
		for(S s : N.states()) {
			//log.info("State : "+s);
			buildSequence(s);
			//log.info("Result :"+sequence);
			if(sequence.size() >= 2)
				break;
			else
				sequence.clear();
		}
		return sequence;
	}
	
	private void buildSequence(S s) {
		buildUpstream(s);
		buildDownstream(s);
	}
	
	private void buildUpstream(S source) {
		//log.info("Upstream of "+source+": "+N.to(source));
		if(N.to(source).size() == 1 && N.from(source).size() == 1) {
			T inT = N.to(source).iterator().next();
			if(!inT.isAuto()) {
				sequence.addFirst(inT);
				buildUpstream(inT.source());
			}
		}
	}
	
	private void buildDownstream(S sink) {
		//log.info("Downstream of "+sink+": "+N.from(sink));
		if(N.from(sink).size() == 1 && N.to(sink).size() == 1) {
			T outT = N.from(sink).iterator().next();
			if(!outT.isAuto()) {
				sequence.addLast(outT);
				buildDownstream(outT.sink());
			}
		}
	}
	
	@Override
	public LinkedList<T> midResult() {
		return sequence;
	}

}
