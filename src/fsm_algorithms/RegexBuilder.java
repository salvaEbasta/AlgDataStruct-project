package fsm_algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import commoninterfaces.FiniteStateMachine;
import commoninterfaces.ComponentBuilder;
import commoninterfaces.State;
import commoninterfaces.Transition;
import utility.Constants;

public class RegexBuilder {
	private static final Logger log = loggerSetup();
	
	public static <S extends State, T extends Transition<S>> String relevanceRegex(FiniteStateMachine<S, T> N, ComponentBuilder<S, T> builder) {
		FiniteStateMachine<S, T> M = (FiniteStateMachine<S, T>) N.clone();
		S nq = null;
		if(M.acceptingStates().size() > 1 || M.from(M.acceptingStates().iterator().next()).size() > 0) {
			nq = builder.newState("nq");
			M.insert(nq);
			for(S s:M.acceptingStates())
				M.add(builder.newTransition(s.id()+"-"+nq.id(), s, nq));
		}else {
			nq = M.acceptingStates().iterator().next();
		}
		return destructiveRelevanceRegex(M, builder, nq);
	}
	
	public static <S extends State, T extends Transition<S>> String relevanceRegex(FiniteStateMachine<S, T> N, ComponentBuilder<S, T> builder, S last) {
		return destructiveRelevanceRegex((FiniteStateMachine<S, T>) N.clone(), builder, last);
	}
	
	private static <S extends State, T extends Transition<S>> String destructiveRelevanceRegex(FiniteStateMachine<S, T> N, ComponentBuilder<S, T> builder, S last){
		log.info(RegexBuilder.class.getSimpleName()+"::relevantRegex...");
		log.info("Selected final state: "+last);
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
		S nq = null;
		if(N.from(last).size() > 0) {
			nq = builder.newState("nq");
			N.insert(nq);
			N.add(builder.newTransition(last.id()+"-"+nq.id(), last, nq));
		}else {
			nq = last;
		}
		
		log.info("Post initialization: "+N.toString());
		
		//Single state automa
		if(N.states().size()==1 && N.from(n0).size()==0)
			return Constants.EPSILON;
		
		//To differentiate the id of the new transitions
		int counter = 0;
		
		//Buffer to compose the regex of the new transition
		StringBuilder regexBuilder = new StringBuilder();
		
		//main procedure
		while(N.transitions().size() > 1) {
			regexBuilder.setLength(0);
			
			//Find a path of a single transition to and from a state of the sequence
			LinkedList<T> transitions = TransitionFinder.oneWayPath(N);
			if(transitions.size() > 0) {
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
			}else if(findParallelTransitions(N, transitions)){
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
	
	@Deprecated
	private static <S extends State, T extends Transition<S>> HashMap<String, LinkedList<T>> endPointRelevanceRegex(FiniteStateMachine<S, T> N, ComponentBuilder<S, T> builder) {
		log.info(RegexBuilder.class.getSimpleName()+"::regexForEachAccepting...");
		log.fine("initial: "+N.toString());
		
		//Initialization of N
		S n0 = null;
		if(N.to(N.initialState()).size() > 0) {
			n0 = builder.newState("n0");
			N.insert(n0);
			T t = builder.newTransition("n0-"+N.initialState().id(), n0, N.initialState());
			N.add(t);
			N.setInitial(n0);
		} else {
			n0 = N.initialState();
		}
		
		S nq = builder.newState("nq");
		N.insert(nq);
		N.acceptingStates().forEach(s->{
			T t = builder.newTransition(s.id()+"-"+nq.id(), s, nq);
			N.add(t);
		});
		
		log.info("Post initialization: "+N.toString());
		
		//Initialization of markings
		HashMap<String, LinkedList<T>> markings = new HashMap<String, LinkedList<T>>();
		
		//To differentiate the id of the new transitions
		int counter = 0;
		
		//Buffer to compose the regex of the new transition
		StringBuilder regexBuilder = new StringBuilder();
		
		//main procedure
		while(N.states().size() > 2 || markings.values().stream().anyMatch(l->l.size()>1)) {
			regexBuilder.setLength(0);
			
			//Find a path of a single transition to and from a state of the sequence
			LinkedList<T> transitions = TransitionFinder.oneWayPath(N);
			StringBuilder mark = new StringBuilder();
			if(transitions.size() > 0) {
				log.info("Found one way path: "+transitions);
				T last = transitions.pollLast();
				N.remove(last);
				
				S source = transitions.getFirst().source();
				S sink = last.sink();
				regexBuilder.append("(");
				transitions.forEach(t->{
					N.remove(t);
					regexBuilder.append(t.relevantLabelContent());
				});
				T tmp = builder.newTransition(source.id()+"-"+sink.id()+"_"+counter,
						source,
						sink);
				
				log.info(String.format("last.sink is nq: {%b}, last.source is accepting:{%b}", last.sink().equals(nq), last.source().isAccepting()));
				
				if(last.sink().equals(nq) && last.source().isAccepting()) {
					regexBuilder.append(")");
					if(!markings.containsKey(last.source().id()))
						markings.put(last.source().id(), new LinkedList<T>());
					markings.get(last.source().id()).add(tmp);
				}else {
					regexBuilder.append(last.relevantLabelContent()+")");
				}
				tmp.setRelevantLabel(regexBuilder.toString());
				N.add(tmp);
				log.info("New transition: "+tmp.toString());
				
			//Find transitions that are parallels
			}else if(findParallelTransitions(N, transitions)){
				log.info("Found parallels: "+transitions);
				
				T head = transitions.pop();
				N.remove(head);
				regexBuilder.append("("+head.relevantLabelContent());
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
			}else if(findSameMarkParallelTransitions(markings, transitions, mark)) {
				log.info("Found same mark, parallels: "+transitions);
				
				T head = transitions.pop();
				N.remove(head);
				markings.get(mark.toString()).remove(head);
				
				regexBuilder.append("("+head.relevantLabelContent());
				transitions.forEach(t->{
					N.remove(t);
					markings.get(mark.toString()).remove(t);
					regexBuilder.append("|"+t.relevantLabelContent());
				});
				regexBuilder.append(")");
				T union = builder.newTransition(head.source().id()+"-"+head.sink().id()+"_"+counter,
						head.source(),
						head.sink());
				union.setRelevantLabel(regexBuilder.toString());
				N.add(union);
				markings.get(mark.toString()).add(union);
				
				log.info("New transition: "+union.toString());
			
			}else {
				log.info("Default procedure");
				
				LinkedList<S> states = new LinkedList<S>(N.states());
				S n_tmp = states.pop();
				//n!=n0 && n!=nq
				while(n_tmp.equals(n0) || n_tmp.equals(nq)) 
					n_tmp = states.pop();
				S n = n_tmp;
				log.info("Selected state: "+n);
				N.to(n)
					.stream()
					.filter(r1->!r1.isAuto())
					.forEach(r1->{
						N.from(n)
							.stream()
							.filter(r2->!r2.isAuto())
							.forEach(r2->{
								String id = "t"+r1.id()+"- t"+r2.id();
								T tmp = builder.newTransition(id,
										r1.source(),
										r2.sink());
								if(r2.sink().equals(nq) && n.isAccepting()) {
									if(N.hasAuto(n)) {
										regexBuilder.append("(");
										regexBuilder.append(r1.relevantLabelContent());
										regexBuilder.append("("+N.getAuto(n).relevantLabelContent()+")*");
										regexBuilder.append(")");
									}else {
										regexBuilder.append(r1.relevantLabelContent());
									}
									if(!markings.containsKey(n.id()))
										markings.put(n.id(), new LinkedList<T>());
									markings.get(n.id()).add(tmp);
								}else if(N.hasAuto(n)) {
									regexBuilder.append("(");
									regexBuilder.append(r1.relevantLabelContent());
									regexBuilder.append("("+N.getAuto(n).relevantLabelContent()+")*");
									regexBuilder.append(r2.relevantLabelContent());
									regexBuilder.append(")");
								}else {
									regexBuilder.append("(");
									regexBuilder.append(r1.relevantLabelContent());
									regexBuilder.append(r2.relevantLabelContent());
									regexBuilder.append(")");
								}
								tmp.setRelevantLabel(regexBuilder.toString());
								N.add(tmp);
								
								log.info("New transition: "+tmp.toString());
							});
					});
				N.remove(n);
			}
			counter++;
			
			log.info("After procedure: "+N.toString());
		}
		
		return markings;
	}
	
	private static Logger loggerSetup() {
		Logger log = Logger.getLogger(RegexBuilder.class.getSimpleName());
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		log.setLevel(Level.INFO);
		//log.addHandler(new StreamHandler(System.out, new SimpleFormatter()));
		return log;
	}
	
	private static <S extends State, T extends Transition<S>> boolean findSameMarkParallelTransitions(HashMap<String, LinkedList<T>> marks,
			LinkedList<T> transitions, StringBuilder key) {
		transitions.clear();
		Iterator<String> iter = marks.keySet().iterator();
		while(iter.hasNext()) {
			if(transitions.size() > 1)
				break;
			
			key.setLength(0);
			key.append(iter.next());
			LinkedList<T> marked = new LinkedList<T>(marks.get(key.toString()));
			while(marked.size() > 1) {
				T t = marked.pop();
				marked.forEach(t1->{if(t1.isParallelTo(t)) transitions.add(t1);});
				transitions.add(t);
				
				if(transitions.size() > 1)
					break;
				else
					transitions.clear();
			}
		}
		return transitions.size() > 0;
	}
	
	private static <S extends State, T extends Transition<S>> boolean findParallelTransitions(FiniteStateMachine<S, T> N, LinkedList<T> transitions) {
		transitions.clear();
		transitions.addAll(TransitionFinder.parallelTransitions(N));
		return transitions.size() > 0;
	}
}
