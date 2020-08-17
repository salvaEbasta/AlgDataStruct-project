package spazio_comp_oss_lin;

import java.util.ArrayList;

import comportamentale_fa.ComportamentaleState;
import comportamentale_fa.Event;
import spazio_comportamentale.SpaceState;

public class SpaceStateOss extends SpaceState{
	
	private int index;

	public SpaceStateOss(String id, ArrayList<ComportamentaleState> actualStates, ArrayList<Event> linkEvents) {
		super(id, actualStates, linkEvents);
		index = 0;
	}
	
	
	public boolean isFinalState(int ossSize) {
		return super.isFinalState() && index == ossSize;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append(String.format(" | %d", index));
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		SpaceStateOss other = (SpaceStateOss) obj;
		return super.equals(other) && this.index == other.index;
	}
}
