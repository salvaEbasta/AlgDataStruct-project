package spazio_comportamentale;

import fsm_interfaces.ComponentBuilder;

public class BuilderSpaceComportamentale implements ComponentBuilder<SpaceState, SpaceTransition<SpaceState>>{

	@Override
	public SpaceState newState(String id) {
		return new SpaceState(id);
	}

	@Override
	public SpaceTransition<SpaceState> newTransition(String id, SpaceState source, SpaceState sink) {
		return new SpaceTransition<SpaceState>(id, source, sink);
	}

}
