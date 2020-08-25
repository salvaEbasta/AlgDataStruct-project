package diagnosticatore;

import java.util.concurrent.Callable;

public class DiagnosiLineare implements Callable<String>{
	private ClosureSpace cSpace;
	private String diagnosis;
	
	public DiagnosiLineare(ClosureSpace cSpace) {
		this.cSpace = cSpace;
		this.diagnosis = "";
	}
	
	@Override
	public String call() throws Exception {
		throw new Exception();
	}
	
	
}
