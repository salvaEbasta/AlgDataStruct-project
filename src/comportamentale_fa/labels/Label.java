package comportamentale_fa.labels;

import utility.Constants;

public abstract class Label {
	
	private String symbol;
	private String label;
	
	public Label(String symbol, String label) {
		this.symbol = symbol;
		this.label = label;
	}
	
	public Label(String symbol) {
		this.symbol = symbol;
		this.label = Constants.EPSILON;
	}
	
	public boolean isEmpty() {
		return label.equals(Constants.EPSILON);
	}
	
	public String getLabel() {
		return label;
	}
	
	public Label concat(Label label) {
		this.label = this.label.concat(label.label);
		return this;
	}
	
	public Label concat(String label) {
		this.label = this.label.concat(label);
		return this;
	}
	
	public Label prepend(String label) {
		this.label = label.concat(this.label);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", symbol, label);
	}

	
	@Override
	public boolean equals(Object obj) {
		Label other = (Label) obj;
		return this.symbol.equals(other.symbol) && this.label.equals(other.label);
	}
}
