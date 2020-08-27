package ui.context;

import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;

public class Result {
	private class FinerResult{
		private String diagnosis;
		private double time;
		private long space;
		
		public FinerResult() {diagnosis = null; time =- 1; space =- 1;}
		public double time() {return time;}
		public long space() {return space;}
		public String diagnosis() {return diagnosis;};
		public void setTime(double time) {this.time = time;}
		public void setSpace(long space) {this.space = space;}
		public void setDiagnosis(String diagnosis) {this.diagnosis = diagnosis;}
		public void reset() {diagnosis = null; time =- 1; space =- 1;}
		public boolean isEmpty() {return diagnosis==null&&time<0&&space<0;}
		
		@Override
		public String toString() {return String.format("Diagnosis: %s, ElapsedTime: %.2f, UsedSpace: %d", diagnosis!=null?diagnosis:"", time, space);}
	}
	private SpaceAutomaObsLin obsSpace;
	private FinerResult spaceResult;
	
	private FinerResult diagnosticatoreResult;
	
	public Result() {
		obsSpace = null;
		spaceResult = new FinerResult();
		diagnosticatoreResult = new FinerResult();
	}
	public boolean obsSpaceNoResults() {return spaceResult.isEmpty();}
	public void resetObsSpaceResults() {spaceResult.reset();}
	public boolean diagnosticatoreNoResults() {return diagnosticatoreResult.isEmpty();}
	public void resetDiagnosticatoreResults() {diagnosticatoreResult.reset();}
	public SpaceAutomaObsLin obsSpace() {return obsSpace;}
	public boolean hasObsSpace() {return obsSpace != null;}
	public void setObsSpace(SpaceAutomaObsLin obsSpace) {this.obsSpace = obsSpace;}
	public void setObsSpaceDiagnosis(String diagnosis) {spaceResult.setDiagnosis(diagnosis);}
	public void setObsSpaceTime(double time) {spaceResult.setTime(time);}
	public void setObsSpaceSpace(long space) {spaceResult.setSpace(space);}
	public void setDiagnosticatoreDiagnosis(String diagnosis) {diagnosticatoreResult.setDiagnosis(diagnosis);}
	public void setDiagnosticatoreTime(double time) {diagnosticatoreResult.setTime(time);}
	public void setDiagnosticatoreSpace(long space) {diagnosticatoreResult.setSpace(space);}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Comportamental Space of a Linear Observation: [".concat(spaceResult.toString()).concat("]"));
		sb.append("Diagnosticatore: [".concat(diagnosticatoreResult.toString()).concat("]"));
		return sb.toString();
	}
}
