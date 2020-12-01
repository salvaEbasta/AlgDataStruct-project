package ui.context;

import java.util.concurrent.atomic.AtomicBoolean;

public class Performance {
	
	private static final long MEGABYTE = 1024L * 1024L;
	
	private double time;
	private long startTime;
	private double space;
	private boolean stopped;
	
	private SpaceCalculator sp;
	
	public void start() {
		startTime = System.currentTimeMillis();
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		sp = new SpaceCalculator();
		sp.start();
	}
	
	public void stop() {
		if(startTime != 0 && sp.isRunning()) {
			long finishTime = System.currentTimeMillis();
			time =  (double)(finishTime - startTime)/1000;
			sp.interrupt();
			space = bytesToMegabytes(sp.getSpaceConsumption());
			startTime = 0;
			sp.reset();		
		}
	}
	
	private double bytesToMegabytes(long bytes) {
        return (double) bytes / MEGABYTE;
    }
	
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
	
	public double getTime() {
		return time;
	}
	
	public double getSpace() {
		return space;
	}
	
	public boolean wasStopped() {
		return stopped;
	}

	public class SpaceCalculator implements Runnable {
		 
	    private Thread worker;
	    private AtomicBoolean running = new AtomicBoolean(false);
	    private AtomicBoolean stopped = new AtomicBoolean(false);
	    
	    private long sum;
	    private long prevSpace;
	 
	    public SpaceCalculator() {
	    	sum = 0;
	    	Runtime runtime = Runtime.getRuntime();
			runtime.gc();
	    	prevSpace = runtime.totalMemory() - runtime.freeMemory();
	    } 

	    public void start() {
	        worker = new Thread(this);
	        worker.start();
	    }
	    
	    public void interrupt() {
	        running.set(false);
	        worker.interrupt();
	    }
	 
	    boolean isRunning() {
	        return running.get();
	    }
	 
	    boolean isStopped() {
	        return stopped.get();
	    }
	 
	    public void run() {
	        running.set(true);
	        stopped.set(false);
        	Runtime runtime = Runtime.getRuntime();
	        prevSpace = runtime.totalMemory() - runtime.freeMemory();
	        while (running.get()) {
	        	runtime = Runtime.getRuntime();
	        	runtime.gc();
		    	long newSpace = runtime.totalMemory() - runtime.freeMemory();
		    	if(newSpace > prevSpace)
		    		sum += newSpace - prevSpace;
		    	prevSpace = newSpace;
	            try {
	                Thread.sleep(8);
	            } catch (InterruptedException e){
	                Thread.currentThread().interrupt();
	            }            	
	        }
	        stopped.set(true);
	    }
	    
	    public void reset() {
	    	if(isStopped()) {
	    		sum = 0;
		    	Runtime runtime = Runtime.getRuntime();
				runtime.gc();
		    	prevSpace = runtime.totalMemory() - runtime.freeMemory();
	    	}	    		
	    }
	    
	    public long getSpaceConsumption() {
	    	return sum;
	    }
	}
}
