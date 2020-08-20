package test.spazioComportamentale;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import comportamentale_fa.ComportamentaleFA;
import comportamentale_fa.ComportamentaleFANet;
import comportamentale_fa.ComportamentaleState;
import comportamentale_fa.ComportamentaleTransition;
import comportamentale_fa.Event;
import comportamentale_fa.Link;
import comportamentale_fa.labels.ObservableLabel;
import comportamentale_fa.labels.RelevantLabel;
import spazio_comportamentale.SpaceAutoma;
import spazio_comportamentale.SpaceAutomaComportamentale;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpazioComportamentale;

class TestSilentClosure {
	private static ComportamentaleFANet initialize_pg26() {
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
		
		ComportamentaleTransition t2a = new ComportamentaleTransition("t2a", s20, s21, e2, l2, out, new ObservableLabel("o2"), new RelevantLabel());
		ComportamentaleTransition t2b = new ComportamentaleTransition("t2b", s21, s20, out, new ObservableLabel(), new RelevantLabel("r"));
		c2.add(t2a);
		c2.add(t2b);
		
		ComportamentaleTransition t3a = new ComportamentaleTransition("t3a", s30, s31, out2, new ObservableLabel("o3"), new RelevantLabel());
		ComportamentaleTransition t3b = new ComportamentaleTransition("t3b", s31, s30, e3, l3, new ObservableLabel(), new RelevantLabel());
		ComportamentaleTransition t3c = new ComportamentaleTransition("t3c", s31, s31, e3, l3, new ObservableLabel(), new RelevantLabel("f"));
		c3.add(t3a);
		c3.add(t3b);
		c3.add(t3c);
		
		ArrayList<Link> listLink = new ArrayList<Link>();
		 listLink.add(l2); listLink.add(l3);
		
		return new ComportamentaleFANet(listLink);
	}
	
	private static SpaceAutomaComportamentale build_ComportamenalSpace_pg38() {
		ComportamentaleFANet pg26 = initialize_pg26();
		SpazioComportamentale sc = new SpazioComportamentale(pg26);
		SpaceAutomaComportamentale computedSpace = sc.generaSpazioComportamentale();
		computedSpace.potatura();
		return computedSpace;
	}
	
	@Test
	void test_pg59() {
		SpaceAutomaComportamentale pg38 = build_ComportamenalSpace_pg38();
		HashMap<Integer, SpaceState> ridenominazione = pg38.ridenominazione();
		//find stato 2
		Iterator<Entry<Integer, SpaceState>> iter = ridenominazione.entrySet().iterator();
		int key = -1;
		while(iter.hasNext()) {
			Entry<Integer, SpaceState> e = iter.next();
			if(e.getValue().getEvents().contains(new Event()) && e.getValue().getEvents().contains(new Event("e3")))
				if(e.getValue().getStates().contains(new ComportamentaleState("s21")) && e.getValue().getStates().contains(new ComportamentaleState("s31")))
					key = e.getKey();
		}
		SpaceAutoma<SpaceState> closure = pg38.silentClosure(ridenominazione.get(key));
		
		System.out.println(closure);
	}

}
