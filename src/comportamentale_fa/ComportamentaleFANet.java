package comportamentale_fa;

import java.util.ArrayList;
import java.util.HashMap;

public class ComportamentaleFANet {
	
	private ArrayList<ComportamentaleFA> net;
	private ArrayList<Link> links;
	private SpaceStatus status;
	
	public ComportamentaleFANet(ArrayList<ComportamentaleFA> net, ArrayList<Link> links) {
		this.net = net;
		this.links = links;
		HashMap<ComportamentaleFA, State> actualStates = new HashMap<ComportamentaleFA, State>();
		for(ComportamentaleFA cfa: net) {
			actualStates.put(cfa, cfa.initialState());
		}
		this.status = new SpaceStatus("id", actualStates, links);
	}
	
	public String status() {
		return status.toString();
	}
	
	public String transition(Transition transition) {
		status.update(transition);
		return status.toString();
	}
}
