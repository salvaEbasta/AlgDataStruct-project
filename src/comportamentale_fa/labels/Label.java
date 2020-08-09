package comportamentale_fa.labels;

public abstract class Label {
	
	private static final String EPSILON = "ε";
	
	private String symbol;
	private String label;
	
	public Label(String symbol, String label) {
		this.symbol = symbol;
		this.label = label;
	}
	
	public Label(String symbol) {
		this.symbol = symbol;
		this.label = EPSILON;
	}
	
	public boolean isEmpty() {
		return label.equals(EPSILON);
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", symbol, label);
	}
	

}
