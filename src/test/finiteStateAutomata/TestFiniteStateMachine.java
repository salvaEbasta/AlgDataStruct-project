package test.finiteStateAutomata;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import finite_state_automata.FiniteStateMachine;
import finite_state_automata.LinkedTransitionsFSA;
import finite_state_automata.State;
import finite_state_automata.Transition;

class TestFiniteStateMachine {
	
	@Test
	void building_benchmarkC1() {
		FiniteStateMachine C1 = new LinkedTransitionsFSA("C1");
		assertTrue(C1.id()=="C1");
		
		State _10 = new State("10");
		State _11 = new State("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		
		Set<State> states = C1.states();
		assertTrue(C1.initialState().equals(_10));
		assertTrue(states.size() == 2);
		assertTrue(states.contains(_11) && states.contains(_10));
		
		Set<State> accepting = C1.acceptingStates();
		assertTrue(accepting.size() == 1);
		assertTrue(accepting.contains(_11));
		
		Transition t1a = new Transition("t1a", _10, _11, "a");
		Transition t1b = new Transition("t1b", _11, _10, "b");
		Transition t1c = new Transition("t1c", _10, _11, "c");
		C1.add(t1a);
		C1.add(t1b);
		C1.add(t1c);
		
		Set<Transition> allT = C1.transitions();
		assertTrue(allT.contains(t1a));
		assertTrue(allT.contains(t1b));
		assertTrue(allT.contains(t1c));
		assertTrue(allT.size() == 3);
		
		allT = C1.from(_10);
		assertTrue(allT.contains(t1a));
		assertTrue(allT.contains(t1c));
		assertTrue(allT.size() == 2);
		
		allT = C1.to(_10);
		assertTrue(allT.contains(t1b));
		assertTrue(allT.size() == 1);
		
		assertFalse(C1.hasAuto(_10));
	}
	
	@Test
	void remove_transitions() {
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
		
		C1.remove(t1c);
		assertTrue(!C1.transitions().contains(t1c));
		assertTrue(!C1.to(_11).contains(t1c));
		assertTrue(!C1.from(_10).contains(t1c));
	}

	@Test
	void remove_state() {
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

		assertFalse(C1.remove(_10));
		
		C1.setInitial(null);
		assertTrue(C1.remove(_10));
		assertFalse(C1.transitions().contains(t1c));
		assertFalse(C1.to(_11).contains(t1c));
		assertFalse(C1.from(_10).contains(t1c));
		
		assertTrue(C1.states().size() == 1 && C1.states().contains(_11));
		assertTrue(C1.transitions().size() == 0);
	}
}
