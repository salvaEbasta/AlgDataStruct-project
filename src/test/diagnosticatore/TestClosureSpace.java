package test.diagnosticatore;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalFSM;
import comportamental_fsm.ComportamentalState;
import comportamental_fsm.ComportamentalTransition;
import comportamental_fsm.Event;
import comportamental_fsm.Link;
import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.RelevantLabel;
import diagnosticatore.ClosureSpace;
import diagnosticatore.algorithms.DiagnosticatoreBuilder;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpazioComportamentale;

class TestClosureSpace {
	private static CFSMnetwork initialize_pg26() {
		//AUTOMATA C2
		ComportamentalFSM c2 = new ComportamentalFSM("C2");
		ComportamentalState s20 = new ComportamentalState("20");
		ComportamentalState s21 = new ComportamentalState("21");
		c2.insert(s20);
		c2.insert(s21);
		c2.setInitial(s20);
		
		//AUTOMATA C3
		ComportamentalFSM c3 = new ComportamentalFSM("C3");
		ComportamentalState s30 = new ComportamentalState("30");
		ComportamentalState s31 = new ComportamentalState("31");
		c3.insert(s30);
		c3.insert(s31);
		c3.setInitial(s30);
		
		Event e2 = new Event("e2");
		Event e3 = new Event("e3");
		Link l2 = new Link("L2", c3, c2);
		Link l3 = new Link("L3", c2, c3);
		HashMap<Event, Link> out = new HashMap<Event, Link>();
		out.put(e3, l3);
		HashMap<Event, Link> out2 = new HashMap<Event, Link>();
		out2.put(e2, l2);
		
		ComportamentalTransition t2a = new ComportamentalTransition("t2a", s20, s21, e2, l2, out, new ObservableLabel("o2"), new RelevantLabel());
		ComportamentalTransition t2b = new ComportamentalTransition("t2b", s21, s20, out, new ObservableLabel(), new RelevantLabel("r"));
		c2.add(t2a);
		c2.add(t2b);
		
		ComportamentalTransition t3a = new ComportamentalTransition("t3a", s30, s31, out2, new ObservableLabel("o3"), new RelevantLabel());
		ComportamentalTransition t3b = new ComportamentalTransition("t3b", s31, s30, e3, l3, new ObservableLabel(), new RelevantLabel());
		ComportamentalTransition t3c = new ComportamentalTransition("t3c", s31, s31, e3, l3, new ObservableLabel(), new RelevantLabel("f"));
		c3.add(t3a);
		c3.add(t3b);
		c3.add(t3c);
		
		ArrayList<Link> listLink = new ArrayList<Link>();
		 listLink.add(l2); listLink.add(l3);
		
		return new CFSMnetwork(listLink);
	}
	
	private static SpaceAutomaComportamentale build_ComportamenalSpace_pg38() {
		CFSMnetwork pg26 = initialize_pg26();
		SpazioComportamentale sc = new SpazioComportamentale(pg26);
		SpaceAutomaComportamentale computedSpace = null;
		try {
			computedSpace = sc.call();
			computedSpace.potatura();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return computedSpace;
	}
	
	@Test
	void test_pg69() throws Exception{
		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		ClosureSpace pg69 = new DiagnosticatoreBuilder(pg38).call();
		System.out.println(pg69.toString());
		
		assertTrue(pg69.states().size() == 7);
		assertTrue(pg69.transitions().size() == 12);
		
		assertTrue(pg69.from(pg69.initialState()).size()==1);
	}
}
