package test.finite_state_machine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import finite_state_machine.FiniteState;
import finite_state_machine.FiniteTransition;
import finite_state_machine.LinkedTransitionsFSA;

class TestFiniteStateMachine {
	
	@Test
	void building_benchmarkC1() {
		LinkedTransitionsFSA C1 = new LinkedTransitionsFSA("C1");
		assertTrue(C1.id()=="C1");
		
		FiniteState _10 = new FiniteState("10");
		FiniteState _11 = new FiniteState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		
		Set<FiniteState> states = C1.states();
		assertTrue(C1.initialState().equals(_10));
		assertTrue(states.size() == 2);
		assertTrue(states.contains(_11) && states.contains(_10));
		
		Set<FiniteState> accepting = C1.acceptingStates();
		assertTrue(accepting.size() == 1);
		assertTrue(accepting.contains(_11));
		
		FiniteTransition t1a = new FiniteTransition("t1a", _10, _11);
		t1a.setRelevantLabel("a");
		FiniteTransition t1b = new FiniteTransition("t1b", _11, _10);
		t1b.setRelevantLabel("b");
		FiniteTransition t1c = new FiniteTransition("t1c", _10, _11);
		t1c.setRelevantLabel("c");
		C1.add(t1a);
		C1.add(t1b);
		C1.add(t1c);
		
		Set<FiniteTransition> allT = C1.transitions();
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
		LinkedTransitionsFSA C1 = new LinkedTransitionsFSA("C1");
		FiniteState _10 = new FiniteState("10");
		FiniteState _11 = new FiniteState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		FiniteTransition t1a = new FiniteTransition("t1a", _10, _11);
		t1a.setRelevantLabel("a");
		FiniteTransition t1b = new FiniteTransition("t1b", _11, _10);
		t1b.setRelevantLabel("b");
		FiniteTransition t1c = new FiniteTransition("t1c", _10, _11);
		t1c.setRelevantLabel("c");
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
		LinkedTransitionsFSA C1 = new LinkedTransitionsFSA("C1");
		FiniteState _10 = new FiniteState("10");
		FiniteState _11 = new FiniteState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		FiniteTransition t1a = new FiniteTransition("t1a", _10, _11);
		t1a.setRelevantLabel("a");
		FiniteTransition t1b = new FiniteTransition("t1b", _11, _10);
		t1b.setRelevantLabel("b");
		FiniteTransition t1c = new FiniteTransition("t1c", _10, _11);
		t1c.setRelevantLabel("c");
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
	
	@Test
	void prova() {
		LinkedTransitionsFSA C1 = new LinkedTransitionsFSA("C1");
		FiniteState _10 = new FiniteState("10");
		FiniteState _11 = new FiniteState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		FiniteTransition t1a = new FiniteTransition("t1a", _10, _11);
		t1a.setRelevantLabel("a");
		FiniteTransition t1b = new FiniteTransition("t1b", _11, _10);
		t1b.setRelevantLabel("b");
		FiniteTransition t1c = new FiniteTransition("t1c", _10, _11);
		t1c.setRelevantLabel("c");
		assertTrue(C1.add(t1a));
		assertTrue(C1.add(t1b));
		assertFalse(C1.add(t1a));
		assertTrue(C1.add(t1c));
	}
}
