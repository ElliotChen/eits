package tw.com.dsc.to;

import java.util.ArrayList;
import java.util.List;

public class Series {
	private String key;
	private String label;
	
	public Series() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Series(String key, String label) {
		super();
		this.key = key;
		this.label = label;
	}

	private List<Model> models = new ArrayList<Model>();
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
	public List<Model> getModels() {
		return models;
	}
	public void setModels(List<Model> models) {
		this.models = models;
	}
	public void addModel(Model model) {
		if (null == this.models) {
			this.models = new ArrayList<Model>();
		}
		
		models.add(model);
	}
}
