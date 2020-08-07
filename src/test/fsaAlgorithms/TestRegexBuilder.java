package test.fsaAlgorithms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import finite_state_automata.FiniteStateMachine;
import finite_state_automata.LinkedTransitionsFSA;
import finite_state_automata.State;
import finite_state_automata.Transition;
import fsa_algorithms.RegexBuilder;

class TestRegexBuilder {
	private static FiniteStateMachine build_benchmarkC1() {
		FiniteStateMachine C1 = new LinkedTransitionsFSA("C1");
		State _10 = new State("10");
		State _11 = new State("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		Transition t1a = new Transition("t1a", _10, _11, "a");
		Transition t1b = new Transition("t1b", _11, _10, "b");
		Transition t1c = new Transition("t1c", _10, _11, "c");
		C1.add(t1a);
		C1.add(t1b);
		C1.add(t1c);
		System.out.print(C1.toString());
		return C1;
	}

	@Test
	void test_benchmarkC1() {
		FiniteStateMachine C1 = build_benchmarkC1();
		String result = RegexBuilder.compute(C1);
		
		System.out.println(result);
		assertTrue(true);
	}

}
