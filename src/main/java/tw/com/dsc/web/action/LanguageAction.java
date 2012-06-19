package tw.com.dsc.web.action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.support.LikeCondition;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.service.LanguageService;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Component("languageAction")
@Scope("prototype")
public class LanguageAction extends BaseAction implements Preparable, ModelDriven<Language> {
	
	private static final long serialVersionUID = -8698958337744082698L;
	
	private static final Logger logger = LoggerFactory.getLogger(LanguageAction.class);
	
	private String oid;
	private Language model;
	private Language example;
	private Page<Language> page;

	private Integer pageNo;
	@Autowired
	private LanguageService languageService;
	@Override
	public void prepare() throws Exception {
		
		if (StringUtils.isNotEmpty(oid)) {
			model = this.languageService.findByOid(oid);
		}
		if (null == this.model) {
			model = new Language();
		}
		example = new Language();
		page = new Page<Language>(example);
		page.setDescOrders(new String[] {"system","oid"});
	}

	public String index() {
		if (!ThreadLocalHolder.getOperator().isL3Admin()) {
			return "redirect";
		}
		return this.list();
	}
	
	public String search() {
		if (!ThreadLocalHolder.getOperator().isL3Admin()) {
			return "redirect";
		}
		
		logger.debug("Search for Example[{}] and PageNo[{}]", this.example, this.pageNo);
		if (null != pageNo) {
			page.setPageNo(pageNo);
		} else {
			page.setPageNo(1);
		}
		/*
		if (null!=example && StringUtils.isNotEmpty(example.getOid())) {
			String oid = example.getOid();
			page.getConditions().add(new LikeCondition("oid", oid, LikeMode.ANYWHERE));
			example.setOid(null);
		}
		*/
		this.page = this.languageService.listByPage(page);
		return "language";
	}
	
	public String list() {
		this.page = this.languageService.listByPage(page);
		return "list";
	}
	
	public String update() {
		this.languageService.merge(model);
		this.addActionMessage("Modify Success");
		return this.list();
	}
	
	public String create() {
		this.languageService.create(model);
		this.addActionMessage("Create Success");
		return this.list();
	}
	
	public String delete() {
		this.languageService.delete(model);
		this.addActionMessage("Delete Success");
		return this.list();
	}

	public String ajaxCheckDuplicateName() {
		Boolean result = this.languageService.checkDuplicateName(example);
		this.renderText(result.toString());
		return null;
	}
	
	public String ajaxCheckDuplicateOid() {
		Boolean result = this.languageService.checkDuplicateOid(example);
		this.renderText(result.toString());
		return null;
	}
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Override
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

	public User getCurrentUser() {
		return ThreadLocalHolder.getUser();
	}

	public Page<Language> getPage() {
		return page;
	}

	public void setPage(Page<Language> page) {
		this.page = page;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}
}
