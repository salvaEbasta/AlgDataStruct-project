package comportamentale_fa;

import java.util.ArrayList;

public class ComportamentaleFANet {
	
	private ArrayList<ComportamentaleFA> net;
	private ArrayList<Link> links;
	private SpaceStatus status;
	
	public ComportamentaleFANet(ArrayList<ComportamentaleFA> net, ArrayList<Link> links) {
		this.net = net;
		this.links = links;
		ArrayList<State> actualStates = new ArrayList<State>();
		for(ComportamentaleFA cfa: net) {
			actualStates.add(cfa.actualState());
		}
		this.status = new SpaceStatus("id", actualStates);
	}
}
