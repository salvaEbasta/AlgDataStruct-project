package comportamentale_fa;

import java.util.ArrayList;

public class SpaceStatus {
	
	private String id;
	private ArrayList<State> actualStates;
	private ArrayList<Link> linksStatus;
	
	public SpaceStatus(String id, ArrayList<State> actualStates) {
		this.id = id;
		this.actualStates = actualStates;
		linksStatus = new ArrayList<Link>();
	}
	
	public State updateState(int index, State state) {
		return actualStates.set(index, state);
	}
	
	public void updateLink(int index, Event event) {
		linksStatus.get(index).setEvent(event);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(actualStates.get(0).id());
		for(int i=1; i<actualStates.size(); i++) {
			sb.append(" ").append(actualStates.get(0).id());
		}
		sb.append(" |");
		for(Link link: linksStatus) {
			sb.append(" ").append(link.eventString());
		}
		return sb.toString();
	}
}
