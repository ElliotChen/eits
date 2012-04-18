package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.dao.LanguageDao;
import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.to.LanguageTO;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Component("languageAction")
@Scope("prototype")
public class LanguageAction extends ActionSupport implements Preparable, ModelDriven<Language> {
	
	private static final long serialVersionUID = -8698958337744082698L;
	
	private static final Logger logger = LoggerFactory.getLogger(LanguageAction.class);
	
	private String oid;
	private Language model;
	private Language example;
	private Page<Language> page;

	private Integer pageNo;
	@Autowired
	private LanguageDao languageDao;
	@Override
	public void prepare() throws Exception {
		
		if (StringUtils.isNotEmpty(oid)) {
			model = this.languageDao.findByOid(oid);
		} else {
			model = new Language();
		}
		example = new Language();
		page = new Page<Language>(example);
	}

	public String index() {
		return this.list();
	}
	
	public String search() {
		logger.debug("Search for Example[{}] and PageNo[{}]", this.example, this.pageNo);
		if (null != pageNo) {
			page.setPageNo(pageNo);
		} else {
			page.setPageNo(1);
		}
		this.page = this.languageDao.listByPage(page);
		return "language";
	}
	
	public String list() {
		this.page = this.languageDao.listByPage(page);
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

	public Language getModel() {
		return model;
	}

	public void setModel(Language model) {
		this.model = model;
	}

	public Language getExample() {
		return example;
	}

	public void setExample(Language example) {
		this.example = example;
	}
	
	private void mockList(List<LanguageTO> list) {
		list.add(new LanguageTO("1", "EN", "English"));
		list.add(new LanguageTO("2", "CN", "Chinses"));
	}
	public User getCurrentUser() {
		return ThreadLocalHolder.getUser();
	}

	public Page<Language> getPage() {
		return page;
	}

	public void setPage(Page<Language> page) {
		this.page = page;
	}

	public LanguageDao getLanguageDao() {
		return languageDao;
	}

	public void setLanguageDao(LanguageDao languageDao) {
		this.languageDao = languageDao;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
}
