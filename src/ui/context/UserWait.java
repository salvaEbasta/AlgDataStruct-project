package ui.context;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import spazio_comportamentale.SpaceAutoma;
import ui.stream.InOutStream;
import ui.stream.SimpleStreamAdapter;

public class UserWait<S extends SpaceAutoma> implements Callable<String>{

	private InOutStream io;
	private Future<S> future;
	
	public UserWait(InOutStream io, Future<S> future) {
		try {
			this.io = io.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			io = new SimpleStreamAdapter();
		} 
		this.future = future;
	}
	
	@Override
	public String call() throws Exception {
		String ans = "";
		while(!ans.equals("stop") && !future.isDone()) {
			ans = io.read("Esecuzione in corso... Inserire 'stop' per terminare: ");	
			if(ans.equals("stop") && !future.isDone()) {
				future.cancel(true);
			}
		}
		return ans;
	}

}
