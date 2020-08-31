package fsm_algorithms;

import java.util.HashMap;
import java.util.LinkedList;

import algorithm_interfaces.Algorithm;
import fsm_interfaces.ComponentBuilder;
import fsm_interfaces.FiniteStateMachine;
import fsm_interfaces.State;
import fsm_interfaces.Transition;
import utility.Constants;

public class MultipleRelRegexBuilder <S extends State, T extends Transition<S>> extends Algorithm<String>{
	private FiniteStateMachine<S, T> N;
	private ComponentBuilder<S, T> builder;
	private String regex;
	
	public MultipleRelRegexBuilder(FiniteStateMachine<S, T> N, ComponentBuilder<S, T> builder) {
		this.N = N;
		this.builder = builder;
	}
	
	@Override
	public String call() throws Exception {
		log.info(this.getClass().getSimpleName()+"::MultipleRelRegex...");
		log.fine("initial: "+N.toString());
		
		//Initialization of N
		//Definition of n0
		S n0 = null;
		if(N.to(N.initialState()).size() > 0) {
			n0 = builder.newState("n0");
			N.insert(n0);
			T t = builder.newTransition("n0-"+N.initialState().id(), n0, N.initialState());
			N.add(t);
			N.setInitial(n0);
		}else {
			n0 = N.initialState();
		}
		
		//Definition of nq
		S nq = builder.newState("nq");
		N.insert(nq);
		for(S s:N.acceptingStates())
			N.add(builder.newTransition(s.id()+"-"+nq.id(), s, nq));
		
		log.info("Post initialization: "+N.toString());
		
		//To differentiate the id of the new transitions
		int counter = 0;
		
		//Markings
		HashMap<S, LinkedList<T>> markings = new HashMap<S, LinkedList<T>>();
		
		//Buffer to compose the regex of the new transition
		StringBuilder regexBuilder = new StringBuilder();
		
		//main procedure
		while(N.transitions().size() > 1 || markings.values().stream().anyMatch(l->l.size()>1)) {
			regexBuilder.setLength(0);
			
			//Find a path of a single transition to and from a state of the sequence
			LinkedList<T> transitions = new OneWayPathFinder<S, T>(N).call();
			if(findOneWayPath(transitions, markings)) {
				log.info("Found one way path: "+transitions);
				
				S source = transitions.getFirst().source();
				S sink = transitions.getLast().sink();
				//regexBuilder.append("(");
				transitions.forEach(t->{
					N.remove(t);
					regexBuilder.append(t.relevantLabelContent());
				});
				//regexBuilder.append(")");
				
				T tmp = builder.newTransition(source.id()+"-"+sink.id()+"_"+counter,
						source,
						sink);
				tmp.setRelevantLabel(regexBuilder.toString());
				N.add(tmp);
				log.info("New transition: "+tmp.toString());
				
			//Find transitions that are parallels
			} else if(findOneWayPathMarked(transitions, markings)) {
				
			} else if(findParallelTransitions(transitions)){
				log.info("Found parallels: "+transitions);
				
				T head = transitions.pop();
				N.remove(head);
				regexBuilder.append("(");
				regexBuilder.append(head.relevantLabelContent());
				transitions.forEach(t->{
					N.remove(t);
					regexBuilder.append("|"+t.relevantLabelContent());
				});
				regexBuilder.append(")");
				String id = head.source().id()+"-"+head.sink().id()+"_"+counter;
				T union = builder.newTransition(id,
						head.source(),
						head.sink());
				union.setRelevantLabel(regexBuilder.toString());
				N.add(union);
				
				log.info("New transition: "+union.toString());
			
				//Find transitions that have the same mark and are parallels
			}else {
				log.info("Default procedure");
				
				LinkedList<S> states = new LinkedList<S>(N.states());
				S n_tmp = states.pop();
				//n!=n0 && n!=nq
				while(n_tmp.equals(n0) || n_tmp.equals(nq)) 
					n_tmp = states.pop();
				S n = n_tmp;
				
				log.info("Selected state: "+n);
				
				for(T r1 : N.to(n)) {
					if(r1.isAuto())
						continue;
					for(T r2 : N.from(n)) {
						if(r2.isAuto())
							continue;

						String id = r1.id()+"-"+r2.id()+"_"+counter;
						T tmp = builder.newTransition(id,
								r1.source(),
								r2.sink());
						regexBuilder.setLength(0);
						//regexBuilder.append("(");
						regexBuilder.append(r1.relevantLabelContent());
						if(N.hasAuto(n))
							regexBuilder.append("("+N.getAuto(n).relevantLabelContent()+")*");
						regexBuilder.append(r2.relevantLabelContent());
						//regexBuilder.append(")");
						tmp.setRelevantLabel(regexBuilder.toString());
						N.add(tmp);
						counter++;
						
						log.info("New transition: "+tmp.toString());
					}
				}
				N.remove(n);
			}
			counter++;
			
			log.info("After procedure: "+N.toString());
		}
		
		return N.from(n0).iterator().next().relevantLabelContent();
	}
	
	private boolean findParallelTransitions(LinkedList<T> transitions) throws Exception{
		transitions.clear();
		transitions.addAll(new ParallelTransitionsFinder<S, T>(N).call());
		return transitions.size() > 0;
	}
	
	private boolean findOneWayPath(LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings) throws Exception {
		transitions.clear();
		for(S s : N.states()) {
			//log.info("State : "+s);
			buildSequence(s, transitions, markings);
			//log.info("Result :"+sequence);
			if(transitions.size() > 0)
				break;
			else
				transitions.clear();
		}
		return transitions.size() > 0;
	}
	
	private void buildSequence(S s, LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings) {
		buildUpstream(s, transitions, markings);
		buildDownstream(s, transitions, markings);
	}
	
	private void buildUpstream(S source, LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings) {
		//log.info("Upstream of "+source+": "+N.to(source));
		if(N.to(source).size() == 1 && N.from(source).size() == 1) {
			T inT = N.to(source).iterator().next();
			if(!inT.isAuto() && markings.values().stream().allMatch(l->!l.contains(inT))) {
				transitions.addFirst(inT);
				buildUpstream(inT.source(), transitions, markings);
			}
		}
	}
	
	private void buildDownstream(S sink, LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings) {
		//log.info("Downstream of "+sink+": "+N.from(sink));
		if(N.from(sink).size() == 1 && N.to(sink).size() == 1) {
			T outT = N.from(sink).iterator().next();
			if(!outT.isAuto() && markings.values().stream().allMatch(l->!l.contains(outT))) {
				transitions.addLast(outT);
				buildDownstream(outT.sink(), transitions, markings);
			}
		}
	}
	
	private boolean findOneWayPathMarked(LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings) throws Exception {
		transitions.clear();
		for(S s : N.states()) {
			//log.info("State : "+s);
			buildSequenceMarked(s, transitions, markings);
			//log.info("Result :"+sequence);
			if(transitions.size() > 0)
				break;
			else
				transitions.clear();
		}
		return transitions.size() > 0;
	}
	
	private void buildSequenceMarked(S s, LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings) {
		buildUpstream(s, transitions, markings);
		buildDownstreamMarked(s, transitions, markings);
	}
	
	private void buildDownstreamMarked(S sink, LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings) {
		//log.info("Downstream of "+sink+": "+N.from(sink));
		if(N.from(sink).size() == 1 && N.to(sink).size() == 1) {
			T outT = N.from(sink).iterator().next();
			if(!outT.isAuto()) {
				transitions.addLast(outT);
				if(markings.values().stream().allMatch(l->!l.contains(outT)))
					buildDownstreamMarked(outT.sink(), transitions, markings);
			}
		}
	}
	
	@Override
	public String midResult() {
		String midResult = new String(regex);
		regex = "";
		return midResult;
	}
}
