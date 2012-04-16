package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.to.LanguageTO;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Component("languageAction")
@Scope("prototype")
public class LanguageAction extends ActionSupport implements Preparable {

	private static final long serialVersionUID = -8698958337744082698L;
	private String oid;
	private LanguageTO model;
	private LanguageTO example;

	private List<LanguageTO> languages;

	@Override
	public void prepare() throws Exception {
		if (StringUtils.isNotEmpty(oid)) {
			model = new LanguageTO(oid, "EN", "English");
		} else {
			model = new LanguageTO();
			example = new LanguageTO();
			languages = new ArrayList<LanguageTO>();
		}
	}

	public String index() {
		return this.list();
	}
	
	public String search() {
		this.list();
		return "language";
	}
	
	public String list() {
		this.mockList(this.languages);
		return "list";
	}
	
	public String save() {
		this.addActionMessage("Modify Success");
		return this.list();
	}
	
	public String delete() {
		this.addActionMessage("Delete Success");
		return this.list();
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public LanguageTO getModel() {
		return model;
	}

	public void setModel(LanguageTO model) {
		this.model = model;
	}

	public LanguageTO getExample() {
		return example;
	}

	public void setExample(LanguageTO example) {
		this.example = example;
	}

	public List<LanguageTO> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LanguageTO> languages) {
		this.languages = languages;
	}
	
	private void mockList(List<LanguageTO> list) {
		list.add(new LanguageTO("1", "EN", "English"));
		list.add(new LanguageTO("2", "CN", "Chinses"));
	}
	public User getCurrentUser() {
		return ThreadLocalHolder.getUser();
	}
}
