package test.comportamentale_fa;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import comportamentale_fa.ComportamentaleFA;
import comportamentale_fa.ComportamentaleFANet;
import comportamentale_fa.Event;
import comportamentale_fa.Link;
import comportamentale_fa.ComportamentaleState;
import comportamentale_fa.ComportamentaleTransition;
import comportamentale_fa.labels.ObservableLabel;
import comportamentale_fa.labels.RelevantLabel;
import fsa_algorithms.RegexBuilder;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpazioComportamentale;
import spazio_comportamentale.oss_lineare.BuilderSpaceComportamentaleObsLin;
import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;
import spazio_comportamentale.oss_lineare.SpazioComportamentaleObs;

class TestCFA {

	private ComportamentaleTransition t3a;
	private ComportamentaleTransition t2a;
	
	ComportamentaleFANet initialize() {
		//AUTOMATA C2
		ComportamentaleFA c2 = new ComportamentaleFA("C2");
		ComportamentaleState s20 = new ComportamentaleState("20");
		ComportamentaleState s21 = new ComportamentaleState("21");
		c2.insert(s20);
		c2.insert(s21);
		c2.setInitial(s20);
		
		//AUTOMATA C3
		ComportamentaleFA c3 = new ComportamentaleFA("C3");
		ComportamentaleState s30 = new ComportamentaleState("30");
		ComportamentaleState s31 = new ComportamentaleState("31");
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
		
		t2a = new ComportamentaleTransition("t2a", s20, s21, e2, l2, out, new ObservableLabel("o2"), new RelevantLabel());
		ComportamentaleTransition t2b = new ComportamentaleTransition("t2b", s21, s20, out, new ObservableLabel(), new RelevantLabel("r"));
		
		t3a = new ComportamentaleTransition("t3a", s30, s31, out2, new ObservableLabel("o3"), new RelevantLabel());
		ComportamentaleTransition t3b = new ComportamentaleTransition("t3b", s31, s30, e3, l3, new ObservableLabel(), new RelevantLabel());
		ComportamentaleTransition t3c = new ComportamentaleTransition("t3c", s31, s31, e3, l3, new ObservableLabel(), new RelevantLabel("f"));
		
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
		SpaceAutomaComportamentale computedSpace = sc.generaSpazioComportamentale();
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
	void spazioComportamentaleOssLineare() {		
		ComportamentaleFANet net = initialize();
		SpazioComportamentaleObs sc = new SpazioComportamentaleObs(net);
		ObservableLabel[] obsLin = {new ObservableLabel("o3"), new ObservableLabel("o2")};
		SpaceAutomaObsLin computedSpace = sc.generaSpazioOsservazione(obsLin);
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
	void diagnostica() {
		ComportamentaleFANet net = initialize();
		SpazioComportamentaleObs sc = new SpazioComportamentaleObs(net);
		ObservableLabel[] obsLin = {new ObservableLabel("o3"), new ObservableLabel("o2")};
		SpaceAutomaObsLin computedSpace = sc.generaSpazioOsservazione(obsLin);
		computedSpace.potatura();
		computedSpace.ridenominazione();
		String output = RegexBuilder.relevanceRegex(computedSpace, new BuilderSpaceComportamentaleObsLin());
		//System.out.println("Result: "+output);
		//simplifiedOutput = "(f(r(f)?)?)?" = "eps|(f((r(f|eps))|eps))" = "ε|(f((r(f|ε))|ε))"
		assertTrue(output.equals("((εε)((εε)|(f((r((εε)|(fε)))|ε))))"));
	}
	
	@Test
	void enabledTransitions() {
		ComportamentaleFANet net = initialize();
		Set<ComportamentaleTransition> enabledT = new HashSet<ComportamentaleTransition>();
		enabledT.add(t3a);
		assertTrue(net.enabledTransitions().equals(enabledT));
		net.transitionTo(t3a);
		enabledT.remove(t3a);
		enabledT.add(t2a);
		assertTrue(net.enabledTransitions().equals(enabledT));
	}

}
