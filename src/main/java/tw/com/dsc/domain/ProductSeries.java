package tw.com.dsc.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

public class ProductSeries implements Identifiable<String> {

	private static final long serialVersionUID = 7478608435957388831L;

	private String oid;
	
	private String id;
	
	private String name;
	
	@OneToMany
	@JoinColumn(referencedColumnName="ID", name="SERIES_ID")
	private List<ProductModel> models = new ArrayList<ProductModel>();
	@Override
	public String getOid() {
		return this.oid;
	}

	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ProductModel> getModels() {
		return models;
	}

	public void setModels(List<ProductModel> models) {
		this.models = models;
	}
}
