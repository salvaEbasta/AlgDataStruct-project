package comportamentale_fa;

import java.util.Set;

public class SpazioComportamentale {
	
	private ComportamentaleFANet net;
	private SpaceTree tree;
	
	public SpazioComportamentale(ComportamentaleFANet net) {
		this.net = net;
		tree = new SpaceTree();		
	}
	
	public SpaceTree generaSpazio() {
		if(tree.isEmpty()) {
			SpaceState initial = new SpaceState(Integer.toString(tree.size()), net.getInitialStates(), net.getActiveEvents());
			tree.put(initial);
			buildSpace(initial, net.enabledTransitions());
			net.restoreState(initial);
		}
		return tree;
	}
	
	private void buildSpace(SpaceState state, Set<Transition> enabledTransitions) {
		tree.addOutputTransitions(state, enabledTransitions);
		if(enabledTransitions.size()>1) {
			for(Transition transition: enabledTransitions) {
				net.restoreState(state);
				scattoTransizione(transition); 
			}		
		} else if (enabledTransitions.size()==1)
			scattoTransizione(enabledTransitions.iterator().next());
	}
	
	private void scattoTransizione(Transition transition) {
		net.transitionTo(transition);	
		SpaceState next = new SpaceState(Integer.toString(tree.size()), net.getActualStates(), net.getActiveEvents());
		if(!tree.containsKey(next)) {
			tree.put(next);
			tree.addInputTransition(next, transition);
			buildSpace(next, net.enabledTransitions());	
		}
	}
	
	public boolean potatura() {
		if(tree.isEmpty())
			generaSpazio();
		return tree.potatura();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(SpaceState state: tree.statesSet()) {
			sb.append(state.toString());
			Set<Transition> in = tree.getInputTransitions(state);
			Set<Transition> out = tree.getOutputTransitions(state);
			if(!in.isEmpty()) {
				sb.append("\n\t- Input Transitions: ");
				for(Transition transition: in) {
					sb.append(transition.id().concat(" "));
				}
			}
			if(!out.isEmpty()) {
				sb.append("\n\t- Output Transitions: ");
				for(Transition transition: out) {
					sb.append(transition.id().concat(" "));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
