package comportamentale_fa;

import java.util.ArrayList;

public class Transition {
	
	private Event in;
	private ArrayList<Event> out;
	
	public Transition(Event in, ArrayList<Event> out) {
		this.in = in;
		this.out = out;
	}

}
