package ui.context;

public class Performance {
	
	private static final long MEGABYTE = 1024L * 1024L;
	
	private double time;
	private long startTime;
	private double space;
	private long startSpace;
	private boolean stopped;
	
	public void start() {
		startTime = System.currentTimeMillis();
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		startSpace = runtime.totalMemory() - runtime.freeMemory();
	}
	
	public void stop() {
		if(startTime != 0 && startSpace != 0) {
			long finishTime = System.currentTimeMillis();
			time =  (double)(finishTime - startTime)/1000;
			Runtime runtime = Runtime.getRuntime();
			long finishSpace = runtime.totalMemory() - runtime.freeMemory();
			space = bytesToMegabytes(finishSpace - startSpace);
			startTime = 0;
			startSpace = 0;
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

}
