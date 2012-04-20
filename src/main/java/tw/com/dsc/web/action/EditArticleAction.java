package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.ArticleType;
import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.Source;
import tw.com.dsc.domain.Status;
import tw.com.dsc.to.ArticleTO;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Component("editAction")
@Scope("prototype")
public class EditArticleAction extends ActionSupport implements Preparable, ModelDriven<Article> {
	
	private static final long serialVersionUID = 626334753779135892L;

	private String oid;
	private Article article;
	private Article example;
	private List<Article> unpublishedArticles;
	private List<Article> draftArticles;
	private List<Article> expiredArticles;
	
	private String message;
	public String list() {
		this.unpublishedArticles = new ArrayList<Article>();
		this.mockArticles(unpublishedArticles);
		
		this.draftArticles = new ArrayList<Article>();
		this.mockArticles(draftArticles);
		
		this.expiredArticles = new ArrayList<Article>();
		this.mockArticles(expiredArticles);
		
		return "list";
	}
	
	public String searchUnpublished() {
		this.unpublishedArticles = new ArrayList<Article>();
		this.mockArticles(unpublishedArticles);
		return "unpublished";
	}
	
	public String searchDraft() {
		this.draftArticles = new ArrayList<Article>();
		this.mockArticles(draftArticles);
		return "draft";
	}
	
	public String searchExpired() {
		this.expiredArticles = new ArrayList<Article>();
		this.mockArticles(expiredArticles);
		return "expired";
	}
	
	public String load() {
		article.setOid(new Long(oid));
		article.setArticleId(new ArticleId("123456"));
		article.setSummary("Summary XYZ");
		article.setType(ArticleType.GeneralInfo);
		article.setPublishDate(new Date());
		article.setLanguage(new Language("EN", "English"));
		article.setHitCount(120);
		article.setEntryDate(new Date());
		article.setKeywords("keyword123");
		article.setQuestion("How to restore and clear rom-d on P-663 in English");
		article.setAnswer("The tag provides metadata about the HTML document. Metadata will not be displayed on the page, but will be machine parsable. Meta elements are typically used to specify page description, keywords, author of the document, last modified, and other metadata. The tag always goes inside the element.");
		article.setStatus(Status.Draft);
		article.setNews(Boolean.FALSE);
		article.setSource(Source.OBM);
		return "edit";
	}
	
	public String preCreate() {
		article.setArticleId(new ArticleId("123456"));
		article.setType(ArticleType.GeneralInfo);
		article.setLanguage(new Language("EN", "English"));
		article.setHitCount(0);
		article.setEntryDate(new Date());
		article.setStatus(Status.Draft);
		article.setNews(Boolean.FALSE);
		article.setSource(Source.OBM);
		return "create";
	}
	
	public String previewSave() {
		return "preview";
	}
	
	public String save() {
		this.addActionMessage("Save Success!");
		return this.list();
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public List<Article> getUnpublishedArticles() {
		return unpublishedArticles;
	}
	public void setUnpublishedArticles(List<Article> unpublishedArticles) {
		this.unpublishedArticles = unpublishedArticles;
	}
	public List<Article> getDraftArticles() {
		return draftArticles;
	}
	public void setDraftArticles(List<Article> draftArticles) {
		this.draftArticles = draftArticles;
	}
	public List<Article> getExpiredArticles() {
		return expiredArticles;
	}
	public void setExpiredArticles(List<Article> expiredArticles) {
		this.expiredArticles = expiredArticles;
	}

	public Article getExample() {
		return example;
	}

	public void setExample(Article example) {
		this.example = example;
	}

	@Override
	public void prepare() throws Exception {
		this.example = new Article();
		this.article = new Article();
	}
	
	private void mockArticles(final List<Article> list) {
		Article a1 = new Article();
		a1.setOid(1L);
		a1.setArticleId(new ArticleId("123456"));
		a1.setSummary("All in!");
		a1.setPublishDate(new Date());
		a1.setHitCount(271);
		a1.setLanguage(new Language("EN", "English"));
		a1.setEntryUser("Adam");
		a1.setEntryDate(new Date());

		Article a2 = new Article();
		a2.setOid(2L);
		a1.setArticleId(new ArticleId("234567"));
		a2.setSummary("Don't do this!");
		a2.setPublishDate(new Date());
		a2.setHitCount(157);
		a1.setLanguage(new Language("EN", "English"));
		a2.setEntryUser("Bob");
		a2.setEntryDate(new Date());
		
		list.add(a1);
		list.add(a2);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public User getCurrentUser() {
		return ThreadLocalHolder.getUser();
	}

	@Override
	public Article getModel() {
		return this.article;
	}
	
}
