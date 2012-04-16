package tw.com.dsc.to;

import java.io.Serializable;

public class SelectOption implements Serializable {

	private static final long serialVersionUID = 4339798429807031795L;
	private String id;
	private String label;
	public SelectOption() {
		this("","---");
	}
	
	public SelectOption(String id, String label) {
		this.id = id;
		this.label = label;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
