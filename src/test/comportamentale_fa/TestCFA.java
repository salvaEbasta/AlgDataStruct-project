package test.comportamentale_fa;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import comportamentale_fa.CFA;
import comportamentale_fa.ComportamentaleFA;
import comportamentale_fa.ComportamentaleFANet;
import comportamentale_fa.Event;
import comportamentale_fa.Link;
import comportamentale_fa.State;
import comportamentale_fa.Transition;
import comportamentale_fa.labels.OsservableLabel;
import comportamentale_fa.labels.RelevantLabel;
import spazio_comportamentale.SpaceInterconnections;
import spazio_comportamentale.SpazioComportamentale;

class TestCFA {

	private Transition t3a;
	private Transition t2a;
	
	ComportamentaleFANet initialize() {
		//AUTOMATA C2
		ComportamentaleFA c2 = new CFA("C2");
		State s20 = new State("20");
		State s21 = new State("21");
		c2.insert(s20);
		c2.insert(s21);
		c2.setInitial(s20);
		
		//AUTOMATA C3
		ComportamentaleFA c3 = new CFA("C3");
		State s30 = new State("30");
		State s31 = new State("31");
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
		
		t2a = new Transition("t2a", s20, s21, e2, l2, out, new OsservableLabel("o2"), new RelevantLabel());
		Transition t2b = new Transition("t2b", s21, s20, out, new OsservableLabel(), new RelevantLabel("r"));
		
		t3a = new Transition("t3a", s30, s31, out2, new OsservableLabel("o3"), new RelevantLabel());
		Transition t3b = new Transition("t3b", s31, s30, e3, l3, new OsservableLabel(), new RelevantLabel());
		Transition t3c = new Transition("t3c", s31, s31, e3, l3, new OsservableLabel(), new RelevantLabel("f"));
		
		/*
		 * System.out.println(t2a.toString()); System.out.println(t2b.toString());
		 * System.out.println("\n"); System.out.println(t3a.toString());
		 * System.out.println(t3b.toString()); System.out.println(t3c.toString());
		 */
		
		c2.add(t2a);
		c2.add(t2b);
		
		c3.add(t3a);
		c3.add(t3b);
		c3.add(t3c);
		
		
		ArrayList<Link> listLink = new ArrayList<Link>();
		 listLink.add(l2); listLink.add(l3);
		
		return new ComportamentaleFANet(listLink);
	}
	
	@Test
	void spazioComportamentale() {		
		ComportamentaleFANet net = initialize();
		SpazioComportamentale sc = new SpazioComportamentale(net);
		SpaceInterconnections computedSpace = sc.generaSpazio();
		System.out.println("*************************\n\tPRIMA della POTATURA:\n*************************");	
		System.out.println(sc.toString());	
		sc.potatura();
		System.out.println("*************************\n\tDOPO POTATURA:\n*************************");	
		System.out.println(sc.toString());	
			
	}
	
	@Test
	void enabledTransitions() {
		ComportamentaleFANet net = initialize();
		Set<Transition> enabledT = new HashSet<Transition>();
		enabledT.add(t3a);
		assertTrue(net.enabledTransitions().equals(enabledT));
		net.transitionTo(t3a);
		enabledT.remove(t3a);
		enabledT.add(t2a);
		assertTrue(net.enabledTransitions().equals(enabledT));
	}

}
