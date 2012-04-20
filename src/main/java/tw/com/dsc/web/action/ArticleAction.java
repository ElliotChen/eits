package tw.com.dsc.web.action;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.ArticleType;
import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.Status;
import tw.com.dsc.to.JsonMsg;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Component("articleAction")
@Scope("prototype")
public class ArticleAction extends ActionSupport implements Preparable, ModelDriven<Article> {

	private static final long serialVersionUID = -1334862612883735089L;

	private static final Logger logger = LoggerFactory.getLogger(ArticleAction.class);
	private Article example;
	private String articleId;
	private String ratingNumber;
	private String suggestion;
	private JsonMsg jsonMsg;
	private String oid;
	private Article article;
	private Article sarticle;
	
	private String message;
	@Override
	public void prepare() throws Exception {
		example = new Article();
		article = new Article();
		sarticle = new Article();
		
		if (StringUtils.isNotEmpty(oid)) {
			sarticle.setOid(new Long(oid));
			sarticle.setArticleId(new ArticleId("123456"));
			sarticle.setSummary("Summary XYZ");
			sarticle.setType(ArticleType.GeneralInfo);
			sarticle.setPublishDate(new Date());
			sarticle.setLanguage(new Language("EN", "English"));
			sarticle.setHitCount(120);
			sarticle.setEntryDate(new Date());
			sarticle.setKeywords("keyword123");
			sarticle.setQuestion("How to restore and clear rom-d on P-663 in English");
			sarticle.setAnswer("The tag provides metadata about the HTML document. Metadata will not be displayed on the page, but will be machine parsable. Meta elements are typically used to specify page description, keywords, author of the document, last modified, and other metadata. The tag always goes inside the element.");
			sarticle.setStatus(Status.Draft);
		}
		
	}

	@Override
	public String execute() throws Exception {
		return this.index();
	}

	public String rating() {
		this.jsonMsg = new JsonMsg("Thanks for your rating for articleId");
		return "rating";
	}
	
	public String suggest() {
		this.jsonMsg = new JsonMsg("Thanks for your suggestion, ["+this.suggestion+"]");
		return "rating";
	}
	
	public String preCopy() {
		this.article = new Article();
		return "copy";
	}
	
	public String index() {
		return "index";
	}
	
	public String previewCopy() {
		this.article.setEntryUser(ThreadLocalHolder.getUser().getAccount());

		return "preview";
	}
	
	public String copy() {
		//this.oid = article.getOid();
		return "detail";
	}

	public Article getExample() {
		return example;
	}

	public void setExample(Article example) {
		this.example = example;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getRatingNumber() {
		return ratingNumber;
	}

	public void setRatingNumber(String ratingNumber) {
		this.ratingNumber = ratingNumber;
	}

	public JsonMsg getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(JsonMsg jsonMsg) {
		this.jsonMsg = jsonMsg;
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

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public Article getSarticle() {
		return sarticle;
	}

	public void setSarticle(Article source) {
		this.sarticle = source;
	}
	
}
