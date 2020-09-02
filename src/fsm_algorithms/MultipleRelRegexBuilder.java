package fsm_algorithms;

import java.util.HashMap;
import java.util.LinkedList;

import algorithm_interfaces.Algorithm;
import fsm_interfaces.ComponentBuilder;
import fsm_interfaces.FiniteStateMachine;
import fsm_interfaces.State;
import fsm_interfaces.Transition;

public class MultipleRelRegexBuilder<S extends State, T extends Transition<S>> extends Algorithm<HashMap<S, String>> {
	private FiniteStateMachine<S, T> N;
	private ComponentBuilder<S, T> builder;
	private HashMap<S, String> mapping;
	private S mark;

	public MultipleRelRegexBuilder(FiniteStateMachine<S, T> N, ComponentBuilder<S, T> builder) {
		this.N = N;
		this.builder = builder;
		mapping = new HashMap<S, String>();
	}

	@Override
	public HashMap<S, String> call() throws Exception {
		log.info(this.getClass().getSimpleName() + "::MultipleRelRegex...");
		log.fine("initial: " + N.toString());

		// Initialization of N
		// Definition of n0
		S n0 = null;
		if (N.to(N.initialState()).size() > 0) {
			n0 = builder.newState("n0");
			N.insert(n0);
			T t = builder.newTransition("n0-" + N.initialState().id(), n0, N.initialState());
			N.add(t);
			N.setInitial(n0);
		} else {
			n0 = N.initialState();
		}

		// Definition of nq
		S nq = builder.newState("nq");
		N.insert(nq);
		for (S s : N.acceptingStates())
			N.add(builder.newTransition(s.id() + "-" + nq.id(), s, nq));

		log.info("Post initialization: " + N.toString());

		// To differentiate the id of the new transitions
		int counter = 0;

		// Markings
		HashMap<S, LinkedList<T>> markings = new HashMap<S, LinkedList<T>>();

		// Buffer to compose the regex of the new transition
		StringBuilder regexBuilder = new StringBuilder();

		// main procedure
		while (N.states().size() > 2 || markings.values().stream().anyMatch(l -> l.size() > 1)) {
			regexBuilder.setLength(0);
			mark = null;
			
			// Find a path of a single transition to and from a state of the sequence
			LinkedList<T> transitions = new OneWayPathFinder<S, T>(N).call();
			if (findOneWayPath(transitions, markings)) {
				log.info("Found one way path: " + transitions);

				T last = transitions.pollLast();
				N.remove(last);

				S source = transitions.getFirst().source();
				S sink = last.sink();
				// regexBuilder.append("(");
				transitions.forEach(t -> {
					N.remove(t);
					regexBuilder.append(t.relevantLabelContent());
				});
				// regexBuilder.append(")");

				T tmp = builder.newTransition(source.id() + "-" + sink.id() + "_" + counter, source, sink);

				if (!last.sink().equals(nq) && !last.source().isAccepting()) {
					regexBuilder.append(last.relevantLabelContent());
				} else {
					if (!markings.containsKey(last.source()))
						markings.put(last.source(), new LinkedList<T>());
					markings.get(last.source()).add(tmp);
				}

				tmp.setRelevantLabel(regexBuilder.toString());
				N.add(tmp);

				log.info("New transition: " + tmp.toString());

				// Find one way path were the last is marked
			} else if (findOneWayPathMarked(transitions, markings)) {
				log.info("Found one way path: " + transitions);

				S source = transitions.getFirst().source();
				S sink = transitions.getLast().sink();
				// regexBuilder.append("(");
				transitions.forEach(t -> {
					N.remove(t);
					regexBuilder.append(t.relevantLabelContent());
				});
				// regexBuilder.append(")");

				T tmp = builder.newTransition(source.id() + "-" + sink.id() + "_" + counter, source, sink);
				tmp.setRelevantLabel(regexBuilder.toString());
				N.add(tmp);

				markings.get(mark).remove(transitions.getLast());
				markings.get(mark).add(tmp);
				log.info("New transition: " + tmp.toString());

				// Find transitions that are parallels
			} else if (findParallelTransitions(transitions, markings)) {
				log.info("Found parallels: " + transitions);

				T head = transitions.pop();
				N.remove(head);
				regexBuilder.append("(");
				regexBuilder.append(head.relevantLabelContent());
				transitions.forEach(t -> {
					N.remove(t);
					regexBuilder.append("|" + t.relevantLabelContent());
				});
				regexBuilder.append(")");
				String id = head.source().id() + "-" + head.sink().id() + "_" + counter;
				T union = builder.newTransition(id, head.source(), head.sink());
				union.setRelevantLabel(regexBuilder.toString());
				N.add(union);

				log.info("New transition: " + union.toString());

				// Find marked transitions that are parallel
			} else if (findMarkedParallelTransitions(transitions, markings)) {
				log.info("Found parallels: " + transitions);

				T head = transitions.pop();
				N.remove(head);
				markings.get(mark).remove(head);

				regexBuilder.append("(");
				regexBuilder.append(head.relevantLabelContent());
				for (T t : transitions) {
					N.remove(t);
					markings.get(mark).remove(t);
					regexBuilder.append("|" + t.relevantLabelContent());
				}
				regexBuilder.append(")");
				String id = head.source().id() + "-" + head.sink().id() + "_" + counter;
				T union = builder.newTransition(id, head.source(), head.sink());
				union.setRelevantLabel(regexBuilder.toString());
				N.add(union);

				markings.get(mark).add(union);
				log.info("New transition: " + union.toString());

				// Find transitions that have the same mark and are parallels
			} else {
				log.info("Default procedure");

				LinkedList<S> states = new LinkedList<S>(N.states());
				S n_tmp = states.pop();
				// n!=n0 && n!=nq
				while (n_tmp.equals(n0) || n_tmp.equals(nq))
					n_tmp = states.pop();
				S n = n_tmp;

				log.info("Selected state: " + n);

				for (T r1 : N.to(n)) {
					if (r1.isAuto())
						continue;
					for (T r2 : N.from(n)) {
						if (r2.isAuto())
							continue;
						if(markings.values().stream().anyMatch(l -> l.contains(r2)))
							continue;

						String id = r1.id() + "-" + r2.id() + "_" + counter;
						T tmp = builder.newTransition(id, r1.source(), r2.sink());
						regexBuilder.setLength(0);

						if (r2.sink().equals(nq) && n.isAccepting()) {
							log.info("Selected mark: " + n.id());

							if (N.hasAuto(n)) {
								regexBuilder.append("(".concat(r1.relevantLabelContent()));
								regexBuilder.append("(".concat(N.getAuto(n).relevantLabelContent()).concat(")*"));
								regexBuilder.append(")");
							} else
								regexBuilder.append(r1.relevantLabelContent());
							
							if(!markings.containsKey(n))
								markings.put(n, new LinkedList<T>());
							markings.get(n).add(tmp);
							
						} else if (N.hasAuto(n)) {
							regexBuilder.append("(".concat(r1.relevantLabelContent()));
							regexBuilder.append("(".concat(N.getAuto(n).relevantLabelContent()).concat(")*"));
							regexBuilder.append(r2.relevantLabelContent().concat(")"));
						} else {
							regexBuilder.append("(".concat(r1.relevantLabelContent()));
							regexBuilder.append(r2.relevantLabelContent().concat(")"));
						}

						tmp.setRelevantLabel(regexBuilder.toString());
						N.add(tmp);
						counter++;

						log.info("New transition: " + tmp.toString());
					}
					
					for(T r2 : N.from(n)) {
						if(r2.isAuto())
							continue;

						if(markings.values().stream().allMatch(l -> !l.contains(r2)))
							continue;
						
						String id = r1.id() + "-" + r2.id() + "_" + counter;
						T tmp = builder.newTransition(id, r1.source(), r2.sink());
						regexBuilder.setLength(0);
						for (S state : markings.keySet())
							if (markings.get(state).contains(r2)) {
								mark = state;
								break;
							}

						log.info("Selected mark: " + mark.id());

						regexBuilder.append("(".concat(r1.relevantLabelContent()));
						if (N.hasAuto(n))
							regexBuilder.append("(".concat(N.getAuto(n).relevantLabelContent()).concat(")*"));
						regexBuilder.append(r2.relevantLabelContent().concat(")"));

						markings.get(mark).remove(r2);
						markings.get(mark).add(tmp);

						tmp.setRelevantLabel(regexBuilder.toString());
						N.add(tmp);
						counter++;

						log.info("New transition: " + tmp.toString());
					}
				}
				N.remove(n);
			}
			counter++;
			if(counter == 8)
				System.out.println();
			log.info("After procedure: " + N.toString());
		}
		mapping = new HashMap<S, String>();
		markings.forEach((s, l) -> mapping.put(s, l.pop().relevantLabelContent()));

		return mapping;
	}

	private boolean findParallelTransitions(LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings)
			throws Exception {
		transitions.clear();
		// transitions.addAll(new ParallelTransitionsFinder<S, T>(N).call());
		LinkedList<T> sequence = new LinkedList<T>(N.transitions());
		while (sequence.size() > 1) {
			T tmp = sequence.pop();
			if (markings.values().stream().anyMatch(l -> l.contains(tmp)))
				continue;

			sequence.forEach(t -> {
				if (t.isParallelTo(tmp) && markings.values().stream().allMatch(l -> !l.contains(t)))
					transitions.add(t);
			});
			if (transitions.size() > 0) {
				transitions.add(tmp);
				break;
			} else
				transitions.clear();
		}
		return transitions.size() > 0;
	}

	private boolean findOneWayPath(LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings) throws Exception {
		transitions.clear();
		for (S s : N.states()) {
			// log.info("State : "+s);
			buildSequence(s, transitions, markings);
			// log.info("Result :"+sequence);
			if (transitions.size() > 0)
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
		// log.info("Upstream of "+source+": "+N.to(source));
		if (N.to(source).size() == 1 && N.from(source).size() == 1) {
			T inT = N.to(source).iterator().next();
			if (!inT.isAuto() && markings.values().stream().allMatch(l -> !l.contains(inT))) {
				transitions.addFirst(inT);
				buildUpstream(inT.source(), transitions, markings);
			}
		}
	}

	private void buildDownstream(S sink, LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings) {
		// log.info("Downstream of "+sink+": "+N.from(sink));
		if (N.from(sink).size() == 1 && N.to(sink).size() == 1) {
			T outT = N.from(sink).iterator().next();
			if (!outT.isAuto() && markings.values().stream().allMatch(l -> !l.contains(outT))) {
				transitions.addLast(outT);
				buildDownstream(outT.sink(), transitions, markings);
			}
		}
	}

	private boolean findOneWayPathMarked(LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings)
			throws Exception {
		transitions.clear();
		for (S s : N.states()) {
			// log.info("State : "+s);
			buildSequenceMarked(s, transitions, markings);
			// log.info("Result :"+sequence);
			if (transitions.size() > 0)
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
		// log.info("Downstream of "+sink+": "+N.from(sink));
		if (N.from(sink).size() == 1 && N.to(sink).size() == 1) {
			T outT = N.from(sink).iterator().next();
			if (!outT.isAuto()) {
				transitions.addLast(outT);
				boolean marked = false;

				for (S state : markings.keySet())
					if (markings.get(state).contains(outT)) {
						marked = true;
						mark = state;
						break;
					}
				if (!marked)
					buildDownstreamMarked(outT.sink(), transitions, markings);
			}
		}
	}

	private boolean findMarkedParallelTransitions(LinkedList<T> transitions, HashMap<S, LinkedList<T>> markings)
			throws Exception {
		for (S state : markings.keySet()) {
			LinkedList<T> marked = new LinkedList<T>(markings.get(state));

			while (marked.size() > 1) {
				transitions.clear();
				T tmp = marked.pop();
				marked.forEach(t -> {
					if (t.isParallelTo(tmp))
						transitions.add(t);
				});
				if (transitions.size() > 0) {
					transitions.add(tmp);
					mark = state;
					break;
				}
			}
			if (transitions.size() > 0)
				break;
		}
		return transitions.size() > 0;
	}

	@Override
	public HashMap<S, String> midResult() {
		return new HashMap<S, String>(mapping);
	}
}
