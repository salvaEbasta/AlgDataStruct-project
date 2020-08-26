package test.finite_state_machine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import comportamental_fsm.ComportamentalFSM;
import comportamental_fsm.ComportamentalState;
import comportamental_fsm.ComportamentalTransition;
import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.RelevantLabel;

class TestComportamentaleFA {
	
	@Test
	void building_benchmarkC1() {
		ComportamentalFSM C1 = new ComportamentalFSM("C1");
		assertTrue(C1.id().equals("C1"));
		
		ComportamentalState _10 = new ComportamentalState("10");
		ComportamentalState _11 = new ComportamentalState("11");
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		
		Set<ComportamentalState> states = C1.states();
		assertTrue(C1.initialState().equals(_10));
		assertTrue(states.size() == 2);
		assertTrue(states.contains(_11) && states.contains(_10));
		
		Set<ComportamentalState> accepting = C1.acceptingStates();
		assertTrue(accepting.isEmpty());

		
		ComportamentalTransition t1a = new ComportamentalTransition("t1a", _10, _11, null, null, new ObservableLabel("o"), new RelevantLabel("a"));
		ComportamentalTransition t1b = new ComportamentalTransition("t1b", _11, _10, null, null, new ObservableLabel(), new RelevantLabel("b"));
		ComportamentalTransition t1c = new ComportamentalTransition("t1c", _10, _11, null, null, new ObservableLabel(), new RelevantLabel("c"));
		C1.add(t1a);
		C1.add(t1b);
		C1.add(t1c);
		
		Set<ComportamentalTransition> allT = C1.transitions();
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
		ComportamentalFSM C1 = new ComportamentalFSM("C1");
		ComportamentalState _10 = new ComportamentalState("10");
		ComportamentalState _11 = new ComportamentalState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		ComportamentalTransition t1a = new ComportamentalTransition("t1a", _10, _11, null, null, new ObservableLabel("o"), new RelevantLabel("a"));
		ComportamentalTransition t1b = new ComportamentalTransition("t1b", _11, _10, null, null, new ObservableLabel(), new RelevantLabel("b"));
		ComportamentalTransition t1c = new ComportamentalTransition("t1c", _10, _11, null, null, new ObservableLabel(), new RelevantLabel("c"));
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
		ComportamentalFSM C1 = new ComportamentalFSM("C1");
		ComportamentalState _10 = new ComportamentalState("10");
		ComportamentalState _11 = new ComportamentalState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		ComportamentalTransition t1a = new ComportamentalTransition("t1a", _10, _11, null, null, new ObservableLabel("o"), new RelevantLabel("a"));
		ComportamentalTransition t1b = new ComportamentalTransition("t1b", _11, _10, null, null, new ObservableLabel(), new RelevantLabel("b"));
		ComportamentalTransition t1c = new ComportamentalTransition("t1c", _10, _11, null, null, new ObservableLabel(), new RelevantLabel("c"));
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
	void addPresentState() {
		ComportamentalFSM C1 = new ComportamentalFSM("C1");
		ComportamentalState _11 = new ComportamentalState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		assertFalse(C1.insert(_11));		
	}
	
	@Test
	void addNotPresentState() {
		ComportamentalFSM C1 = new ComportamentalFSM("C1");
		ComportamentalState _10 = new ComportamentalState("10");
		ComportamentalState _11 = new ComportamentalState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		assertTrue(C1.insert(_10));		
	}
	
	@Test
	void addPresentTransition() {
		ComportamentalFSM C1 = new ComportamentalFSM("C1");
		ComportamentalState _10 = new ComportamentalState("10");
		ComportamentalState _11 = new ComportamentalState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		ComportamentalTransition t1a = new ComportamentalTransition("t1a", _10, _11, null, null, new ObservableLabel("o"), new RelevantLabel("a"));
		ComportamentalTransition t1b = new ComportamentalTransition("t1b", _11, _10, null, null, new ObservableLabel(), new RelevantLabel("b"));

		C1.add(t1a);
		C1.add(t1b);
		assertFalse(C1.add(t1a));
	}
	
	@Test
	void addNotPresentTransition() {
		ComportamentalFSM C1 = new ComportamentalFSM("C1");
		ComportamentalState _10 = new ComportamentalState("10");
		ComportamentalState _11 = new ComportamentalState("11");
		_11.setAccepting(true);
		C1.insert(_11);
		C1.insert(_10);
		C1.setInitial(_10);
		ComportamentalTransition t1a = new ComportamentalTransition("t1a", _10, _11, null, null, new ObservableLabel("o"), new RelevantLabel("a"));
		ComportamentalTransition t1b = new ComportamentalTransition("t1b", _11, _10, null, null, new ObservableLabel(), new RelevantLabel("b"));
		ComportamentalTransition t1c = new ComportamentalTransition("t1c", _10, _11, null, null, new ObservableLabel(), new RelevantLabel("c"));
		
		C1.add(t1a);
		C1.add(t1b);
		assertTrue(C1.add(t1c));
	}
}
