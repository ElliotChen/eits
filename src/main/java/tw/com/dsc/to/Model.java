package tw.com.dsc.to;

public class Model {
	private String key;
	private String label;
	
	public Model() {
		super();
	}
	
	public Model(String key, String label) {
		super();
		this.key = key;
		this.label = label;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
