package algorithm_interfaces;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Algorithm<O> implements Callable<O>, MidResult<O> {
	protected final Logger log;
	
	public Algorithm() {
		log = setupLog();
	}
	
	private Logger setupLog() {
		Logger log = Logger.getLogger(this.getClass().getSimpleName());
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		log.setLevel(Level.INFO);
		return log;
	}
}
