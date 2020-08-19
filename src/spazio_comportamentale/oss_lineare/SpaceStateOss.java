package spazio_comportamentale.oss_lineare;

import java.util.ArrayList;

import comportamentale_fa.ComportamentaleState;
import comportamentale_fa.Event;
import spazio_comportamentale.SpaceState;

public class SpaceStateOss extends SpaceState{
	
	private int index;
	private int ossSize;

	public SpaceStateOss(String id, ArrayList<ComportamentaleState> actualStates, ArrayList<Event> linkEvents, int index, int ossSize) {
		super(id, actualStates, linkEvents);
		this.index = index;
		this.ossSize = ossSize;	
	}
	
	@Override
	public boolean isFinal() {
		return super.isFinal() && index == ossSize;
	}

	@Override
	public String toString() {
		String base = super.toString().replace("\t[Stato Finale]", "");
		StringBuilder sb = new StringBuilder(base);
		sb.append(String.format(" | %d", index));
		sb.append(isFinal()? "\t[Stato Finale]": "");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		SpaceStateOss other = (SpaceStateOss) obj;
		return super.equals(other) && this.index == other.index && this.ossSize == other.ossSize;
	}
}
