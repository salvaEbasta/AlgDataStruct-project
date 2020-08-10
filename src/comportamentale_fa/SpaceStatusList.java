package comportamentale_fa;

import java.util.ArrayList;
import java.util.Set;

public class SpaceStatusList {
	
	private ArrayList<SpaceStatus> listStatus;
	
	public SpaceStatusList() {
		listStatus = new ArrayList<SpaceStatus>();
	}
	
	public boolean add(ArrayList<State> actualStates, ArrayList<Event> linkEvents) {
		return add(actualStates, linkEvents, null);
	}
	
	public boolean add(ArrayList<State> actualStates, ArrayList<Event> linkEvents, Transition inputTransition) {
		SpaceStatus status = new SpaceStatus(Integer.toString(listStatus.size()), actualStates, linkEvents);
		if(!listStatus.contains(status)) {			
			if(inputTransition != null)
				status.addInputTransition(inputTransition);
			System.out.println(status);
			return listStatus.add(status);				
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(SpaceStatus status: listStatus) {
			sb.append(status.toString()).append("\n");
		}
		return sb.toString();
	}

	public boolean addOutputTransitions(Set<Transition> enabledTransitions) {
		SpaceStatus status = listStatus.get(listStatus.size()-1);
		return status.addOutputTransition(enabledTransitions);
	}

}
