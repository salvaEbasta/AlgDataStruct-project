package diagnosticatore;

import java.util.Set;

import commoninterfaces.FiniteStateMachine;
import commoninterfaces.Interconnections;
import spazio_comportamentale.SpaceState;
import spazio_comportamentale.SpaceTransition;

public class SilentClosure implements FiniteStateMachine<SpaceState, SpaceTransition<SpaceState>>{
	private String id;
	private SpaceState initial;
	private Interconnections<SpaceState, SpaceTransition<SpaceState>> structure;
	private Interconnections<SpaceState, SpaceTransition<SpaceState>> toOutside;
	
	
	public SilentClosure(String id) {
		this.id = id;
		this.structure = new Interconnections<SpaceState, SpaceTransition<SpaceState>>();
		this.toOutside = new Interconnections<SpaceState, SpaceTransition<SpaceState>>();
		this.initial = null;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public Set<SpaceTransition<SpaceState>> transitions() {
		Set<SpaceTransition<SpaceState>> tmp = structure.transitions();
		tmp.addAll(toOutside.transitions());
		return tmp;
	}

	@Override
	public Set<SpaceState> states() {
		return structure.allKeys;
	}

	@Override
	public SpaceState initialState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpaceState currentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<SpaceTransition<SpaceState>> to(SpaceState s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<SpaceTransition<SpaceState>> from(SpaceState s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(SpaceTransition<SpaceState> t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(SpaceState s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setInitial(SpaceState s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(SpaceTransition<SpaceState> t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(SpaceState s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean transitionTo(SpaceTransition<SpaceState> t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setCurrent(SpaceState s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<SpaceState> acceptingStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAuto(SpaceState s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SpaceTransition<SpaceState> getAuto(SpaceState s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasState(SpaceState s) {
		// TODO Auto-generated method stub
		return false;
	}
}
