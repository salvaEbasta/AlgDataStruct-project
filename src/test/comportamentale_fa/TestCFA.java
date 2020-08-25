package test.comportamentale_fa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import comportamental_fsm.ComportamentalFSM;
import comportamental_fsm.CFSMnetwork;
import comportamental_fsm.ComportamentalState;
import comportamental_fsm.ComportamentalTransition;
import comportamental_fsm.Event;
import comportamental_fsm.Link;
import comportamental_fsm.labels.ObservableLabel;
import comportamental_fsm.labels.ObservationsList;
import comportamental_fsm.labels.RelevantLabel;
import fsm_algorithms.RegexBuilder;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpazioComportamentale;
import spazio_comportamentale.oss_lineare.BuilderSpaceComportamentaleObsLin;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpazioComportamentaleObs;

class TestCFA {

	private ComportamentalTransition t3a;
	private ComportamentalTransition t2a;
	private ComportamentalTransition t2b;
	
	CFSMnetwork initialize() {
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
		
		t2a = new ComportamentalTransition("t2a", s20, s21, e2, l2, out, new ObservableLabel("o2"), new RelevantLabel());
		t2b = new ComportamentalTransition("t2b", s21, s20, out, new ObservableLabel(), new RelevantLabel("r"));
		
		t3a = new ComportamentalTransition("t3a", s30, s31, out2, new ObservableLabel("o3"), new RelevantLabel());
		ComportamentalTransition t3b = new ComportamentalTransition("t3b", s31, s30, e3, l3, new ObservableLabel(), new RelevantLabel());
		ComportamentalTransition t3c = new ComportamentalTransition("t3c", s31, s31, e3, l3, new ObservableLabel(), new RelevantLabel("f"));
		
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
		
		return new CFSMnetwork(listLink);
	}
	
	@Test
	void spazioComportamentale() throws Exception{		
		CFSMnetwork net = initialize();
		SpazioComportamentale sc = new SpazioComportamentale(net);
		SpaceAutomaComportamentale computedSpace = sc.call();
		System.out.println("*************************\n\tPRIMA della POTATURA:\n*************************");	
		System.out.println(computedSpace.toString());	
		computedSpace.potatura();
		System.out.println("*************************\n\tDOPO POTATURA:\n*************************");	
		System.out.println(computedSpace);	
		System.out.println("*************************\n\tRIDENOMINAZIONE:\n*************************");
		System.out.println(computedSpace.ridenominazione());
		System.out.println("*************************\n\tDOPO RIDENOMINAZIONE:\n*************************");	
		System.out.println(computedSpace);
	}
	
	@Test
	void spazioComportamentaleOssLineare() throws Exception{		
		CFSMnetwork net = initialize();
		ObservationsList obsLin = new ObservationsList();
		obsLin.add(new ObservableLabel("o3"));
		obsLin.add(new ObservableLabel("o2"));
		SpazioComportamentaleObs sc = new SpazioComportamentaleObs(net, obsLin);
		SpaceAutomaObsLin computedSpace = sc.call();
		System.out.println("*************************\n\tPRIMA della POTATURA:\n*************************");	
		System.out.println(computedSpace.toString());	
		computedSpace.potatura();
		System.out.println("*************************\n\tDOPO POTATURA:\n*************************");	
		System.out.println(computedSpace.toString());
		computedSpace.ridenominazione();
		System.out.println("*************************\n\tDOPO RIDENOMINAZIONE:\n*************************");	
		System.out.println(computedSpace);		
	}
	
	@Test
	void diagnostica() throws Exception{
		CFSMnetwork net = initialize();
		ObservationsList obsLin = new ObservationsList();
		obsLin.add(new ObservableLabel("o3"));
		obsLin.add(new ObservableLabel("o2"));
		SpazioComportamentaleObs sc = new SpazioComportamentaleObs(net, obsLin);
		SpaceAutomaObsLin computedSpace = sc.call();
		computedSpace.potatura();
		computedSpace.ridenominazione();
		String output = RegexBuilder.relevanceRegex(computedSpace, new BuilderSpaceComportamentaleObsLin());
		System.out.println("Result: "+output);
		//simplifiedOutput = "(f(r(f)?)?)?" = "eps|(f((r(f|eps))|eps))" = "ε|(f((r(f|ε))|ε))"
		assertTrue(output.equals("εε(f(r(εε|fε)|ε)|εε)"));
	}
	
	@Test
	void enabledTransitions() {
		CFSMnetwork net = initialize();
		Set<ComportamentalTransition> enabledT = new HashSet<ComportamentalTransition>();
		enabledT.add(t3a);
		assertTrue(net.enabledTransitions().equals(enabledT));
		net.transitionTo(t3a);
		enabledT.remove(t3a);
		enabledT.add(t2a);
		assertTrue(net.enabledTransitions().equals(enabledT));
	}
	
	@Test
	void test_silentTransitions() {
		CFSMnetwork net = initialize();
		assertTrue(!t3a.isSilent());
		assertTrue(!t2a.isSilent());
		assertTrue(t2b.isSilent());
	}

}
