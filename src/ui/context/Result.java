package ui.context;

import java.io.Serializable;

import spazio_comportamentale.oss_lineare.SpaceAutomaObsLin;

public class Result implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private class FinerResult implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String diagnosis;
		private double time;
		private double space;
		
		public FinerResult() {diagnosis = null; time =- 1; space =- 1;}
		public void setTime(double time) {this.time = time;}
		public void setSpace(double space) {this.space = space;}
		public void setDiagnosis(String diagnosis) {this.diagnosis = diagnosis;}
		public void reset() {diagnosis = null; time =- 1; space =- 1;}
		public boolean isEmpty() {return diagnosis==null&&time<0&&space<0;}
		
		@Override
		public String toString() {return String.format("Diagnosis: %s, Time: %.2fs, Space: %.2fMB", diagnosis!=null?diagnosis:"", time, space);}
	}
	private SpaceAutomaObsLin obsSpace;
	private double obsSpaceTime;
	private double obsSpaceSpace;
	private FinerResult spaceResult;
	
	private FinerResult diagnosticatoreResult;
	
	public Result() {
		obsSpace = null;
		spaceResult = new FinerResult();
		diagnosticatoreResult = new FinerResult();
		obsSpaceTime = -1;
		obsSpaceSpace = -1;
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
	public void setObsSpaceSpace(double space) {spaceResult.setSpace(space);}
	public void setDiagnosticatoreDiagnosis(String diagnosis) {diagnosticatoreResult.setDiagnosis(diagnosis);}
	public void setDiagnosticatoreTime(double time) {diagnosticatoreResult.setTime(time);}
	public void setDiagnosticatoreSpace(double space) {diagnosticatoreResult.setSpace(space);}
	public void setObsSpaceGenerationTime(double time) {obsSpaceTime = time;}
	public void setObsSpaceGenerationSpace(double space) {obsSpaceSpace = space;}
	public double obsSpaceGenTime() {return obsSpaceTime;}
	public double obsSpaceGenSpace() {return obsSpaceSpace;}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("LinearObs Space: [time: %.2fs, space: %.2fMB]\n", obsSpaceTime, obsSpaceSpace));
		sb.append("\t\tDiagnosis with LinearObs comp Space: [".concat(spaceResult.toString()).concat("]\n"));
		sb.append("\t\tDiagnosis with Diagnosticatore: [".concat(diagnosticatoreResult.toString()).concat("]"));
		return sb.toString();
	}
}
