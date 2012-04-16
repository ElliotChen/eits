package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.to.ArticleTO;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Component("editAction")
@Scope("prototype")
public class EditArticleAction extends ActionSupport implements Preparable, ModelDriven<ArticleTO> {
	
	private static final long serialVersionUID = 626334753779135892L;

	private String oid;
	private ArticleTO article;
	private ArticleTO example;
	private List<ArticleTO> unpublishedArticles;
	private List<ArticleTO> draftArticles;
	private List<ArticleTO> expiredArticles;
	
	private String message;
	public String list() {
		this.unpublishedArticles = new ArrayList<ArticleTO>();
		this.mockArticles(unpublishedArticles);
		
		this.draftArticles = new ArrayList<ArticleTO>();
		this.mockArticles(draftArticles);
		
		this.expiredArticles = new ArrayList<ArticleTO>();
		this.mockArticles(expiredArticles);
		
		return "list";
	}
	
	public String searchUnpublished() {
		this.unpublishedArticles = new ArrayList<ArticleTO>();
		this.mockArticles(unpublishedArticles);
		return "unpublished";
	}
	
	public String searchDraft() {
		this.draftArticles = new ArrayList<ArticleTO>();
		this.mockArticles(draftArticles);
		return "draft";
	}
	
	public String searchExpired() {
		this.expiredArticles = new ArrayList<ArticleTO>();
		this.mockArticles(expiredArticles);
		return "expired";
	}
	
	public String load() {
		article.setOid(oid);
		article.setId("19765");
		article.setSummary("Summary XYZ");
		article.setType("General");
		article.setPublishDate(new Date());
		article.setLanguage("English");
		article.setHitCount(120);
		article.setEntryDate(new Date());
		article.setKeywords("keyword123");
		article.setQuestion("How to restore and clear rom-d on P-663 in English");
		article.setAnswer("The tag provides metadata about the HTML document. Metadata will not be displayed on the page, but will be machine parsable. Meta elements are typically used to specify page description, keywords, author of the document, last modified, and other metadata. The tag always goes inside the element.");
		article.setNews("false");
		article.setSource("1");
		article.setState("Final");
		return "edit";
	}
	
	public String empty() {
		article.setOid("");
		article.setId("");
		article.setSummary("");
		article.setType("General");
		article.setLanguage("English");
		article.setKeywords("");
		article.setQuestion("");
		article.setAnswer("");
		article.setNews("false");
		article.setSource("1");
		article.setState("Final");
		return "edit";
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
	public ArticleTO getArticle() {
		return article;
	}
	public void setArticle(ArticleTO article) {
		this.article = article;
	}
	public List<ArticleTO> getUnpublishedArticles() {
		return unpublishedArticles;
	}
	public void setUnpublishedArticles(List<ArticleTO> unpublishedArticles) {
		this.unpublishedArticles = unpublishedArticles;
	}
	public List<ArticleTO> getDraftArticles() {
		return draftArticles;
	}
	public void setDraftArticles(List<ArticleTO> draftArticles) {
		this.draftArticles = draftArticles;
	}
	public List<ArticleTO> getExpiredArticles() {
		return expiredArticles;
	}
	public void setExpiredArticles(List<ArticleTO> expiredArticles) {
		this.expiredArticles = expiredArticles;
	}

	public ArticleTO getExample() {
		return example;
	}

	public void setExample(ArticleTO example) {
		this.example = example;
	}

	@Override
	public void prepare() throws Exception {
		this.example = new ArticleTO();
		this.article = new ArticleTO();
	}
	
	private void mockArticles(final List<ArticleTO> list) {
		ArticleTO a1 = new ArticleTO();
		a1.setOid("1");
		a1.setId("19230");
		a1.setSummary("All in!");
		a1.setPublishDate(new Date());
		a1.setHitCount(271);
		a1.setLanguage("EN");
		a1.setEntryUser("Adam");
		a1.setEntryDate(new Date());

		ArticleTO a2 = new ArticleTO();
		a2.setOid("2");
		a2.setId("15490");
		a2.setSummary("Don't do this!");
		a2.setPublishDate(new Date());
		a2.setHitCount(157);
		a2.setLanguage("EN");
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
	public ArticleTO getModel() {
		return this.article;
	}
	
}
