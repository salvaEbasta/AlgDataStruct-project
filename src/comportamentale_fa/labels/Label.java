package comportamentale_fa.labels;

public abstract class Label {
	
	private String symbol;
	private String label;
	
	public Label(String symbol, String label) {
		this.symbol = symbol;
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", symbol, label);
	}
	

}
