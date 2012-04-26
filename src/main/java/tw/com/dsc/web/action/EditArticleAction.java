package tw.com.dsc.web.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.ActionType;
import tw.com.dsc.domain.Article;
import tw.com.dsc.domain.ArticleId;
import tw.com.dsc.domain.ArticleLog;
import tw.com.dsc.domain.ArticleType;
import tw.com.dsc.domain.Attachment;
import tw.com.dsc.domain.Language;
import tw.com.dsc.domain.Source;
import tw.com.dsc.domain.Status;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.service.ArticleLogService;
import tw.com.dsc.service.ArticleService;
import tw.com.dsc.service.AttachmentService;
import tw.com.dsc.service.LanguageService;
import tw.com.dsc.to.User;
import tw.com.dsc.util.ThreadLocalHolder;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Component("editAction")
@Scope("prototype")
public class EditArticleAction extends BaseAction implements Preparable, ModelDriven<Article>, ServletContextAware {
	private static final Logger logger = LoggerFactory.getLogger(EditArticleAction.class);
	private static final long serialVersionUID = 626334753779135892L;

	private Long oid;
	private Article article;
	private Article example;
	private Page<Article> page;
	private Page<Article> unpublishedArticles;
	private Page<Article> draftArticles;
	private Page<Article> expiredArticles;
	
	private String statusAction;
	
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	private List<Language> languages;
	private List<ArticleLog> articleLogs;
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private ArticleLogService articleLogService;
	private ServletContext context;
	private String message;
	private String rejectReason;
	private String articleIdOid;
	@Override
	public void prepare() throws Exception {
		if (null != this.oid) {
			this.article = this.articleService.findByOid(oid);
		}
		this.example = new Article();
		if (null == this.article) {
			this.article = new Article();
			this.article.setArticleId(new ArticleId());
			this.article.setLanguage(new Language());
			this.article.setNews(Boolean.FALSE);
		}
		page = new Page<Article>(example);
		this.languages = this.languageService.listAll();
	}
	
	public String list() {
		this.unpublishedArticles = this.articleService.searchUnpublishedPage(new Page<Article>(example));
		this.draftArticles = this.articleService.searchDraftPage(new Page<Article>(example));
		this.expiredArticles = this.articleService.searchExpiredPage(new Page<Article>(example));
		
		return "list";
	}
	
	public String searchUnpublished() {
		this.unpublishedArticles = this.articleService.searchUnpublishedPage(page);
		return "unpublished";
	}
	
	public String searchDraft() {
		this.draftArticles = this.articleService.searchDraftPage(page);
		return "draft";
	}
	
	public String searchExpired() {
		this.expiredArticles = this.articleService.searchExpiredPage(page);
		return "expired";
	}
	
	public String load() {
		return "edit";
	}
	
	public String preCreate() {
		article.setArticleId(new ArticleId(""));
		article.setType(ArticleType.GeneralInfo);
		article.setLanguage(new Language("EN", "English"));
		article.setHitCount(0);
		article.setEntryDate(new Date());
//		article.setStatus(Status.Draft);
		article.setNews(Boolean.FALSE);
		article.setSource(Source.OBM);
		return "create";
	}
	
	public String create() {
		Attachment attachment = null;
		if (null != this.upload) {
			String path = "upload/";
			String ctx = this.context.getContextPath();
			try {
				attachment = this.attachmentService.saveAttchment(ctx+"/"+path, uploadFileName, uploadContentType);
				
				String folder = this.context.getRealPath("/")+path;
				File pt = new File(folder);
				if (!pt.exists()) {
					pt.mkdirs();
				}
				FileUtils.copyFile(upload, new File(folder,attachment.getFullName()));
				
			} catch (IOException e) {
				logger.error("File Upload Failed", e);
			}
			
			this.article.setFirmware(attachment);
		}
		
		this.article.setArticleId(new ArticleId(articleIdOid));
		
		if ("Draft".equals(statusAction)) {
			this.articleService.draftNewArticle(article);
		} else if ("WaitForApproving".equals(statusAction)) {
			this.articleService.finalNewArticle(article);
		} else if ("Published".equals(statusAction) ) {
			this.articleService.publishNewL2Article(article);
		} else if ("WaitForProofRead".equals(statusAction)) {
			this.articleService.publishNewL3Article(article);
		} 
		
		return this.list();
	}
	
	public String previewSave() {
		return "preview";
	}
	
	public String preview() {
		return "preview";
	}
	
	public String save() {
		Attachment attachment = null;
		if (null != this.upload) {
			String path = "upload/";
			String ctx = this.context.getContextPath();
			try {
				attachment = this.attachmentService.saveAttchment(ctx+"/"+path, uploadFileName, uploadContentType);
				
				String folder = this.context.getRealPath("/")+path;
				File pt = new File(folder);
				if (!pt.exists()) {
					pt.mkdirs();
				}
				FileUtils.copyFile(upload, new File(folder,attachment.getFullName()));
				
			} catch (IOException e) {
				logger.error("File Upload Failed", e);
			}
			
			this.article.setFirmware(attachment);
		}
		
		this.article.setUpdateDate(new Date());
		
/*
edit.statuAction.Draft = Reject
edit.statuAction.WaitForApproving = Final
edit.statuAction.WaitForProofRead = Approve
edit.statuAction.ReadyToUpdate = ReadyToUpdate
edit.statuAction.ReadyToPublish = ReadytoPublish
edit.statuAction.Published = Publish
 */
				
		if (StringUtils.isEmpty(this.statusAction)) {
			this.articleService.saveOrUpdate(article);
		} else if ("WaitForProofRead".equals(this.statusAction)) {
			this.articleService.approve(article);
		} else if ("Draft".equals(this.statusAction)) {
			this.articleService.reject(article, this.rejectReason);
		} else if ("Published".equals(this.statusAction)) {
			this.articleService.publish(article);
		} else if ("WaitForApproving".equals(this.statusAction)) {
			this.articleService.finalArticle(article);
		} else if ("ReadyToUpdate".equals(this.statusAction)) {
			this.articleService.readyUpdate(article);
		} else if ("ReadyToPublish".equals(this.statusAction)) {
			this.articleService.readyPublish(article);
		}
		this.addActionMessage("Save Success!");
		return this.list();
	}
	public String disable() {
		this.articleService.disable(article);
		return this.list();
	}
	public String viewLogs() {
		ArticleLog exam = new ArticleLog();
		exam.setArticleOid(oid);
		this.articleLogs = this.articleLogService.listByExample(exam);
		return "log";
	}
	
	public String viewRejectLogs() {
		ArticleLog exam = new ArticleLog();
		exam.setArticleOid(oid);
		exam.setAction(ActionType.Reject);
		this.articleLogs = this.articleLogService.listByExample(exam);
		return "log";
	}
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}

	public Page<Article> getPage() {
		return page;
	}

	public void setPage(Page<Article> page) {
		this.page = page;
	}

	public Page<Article> getUnpublishedArticles() {
		return unpublishedArticles;
	}

	public void setUnpublishedArticles(Page<Article> unpublishedArticles) {
		this.unpublishedArticles = unpublishedArticles;
	}

	public Page<Article> getDraftArticles() {
		return draftArticles;
	}

	public void setDraftArticles(Page<Article> draftArticles) {
		this.draftArticles = draftArticles;
	}

	public Page<Article> getExpiredArticles() {
		return expiredArticles;
	}

	public void setExpiredArticles(Page<Article> expiredArticles) {
		this.expiredArticles = expiredArticles;
	}

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public Article getExample() {
		return example;
	}

	public void setExample(Article example) {
		this.example = example;
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

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	public ArticleLogService getArticleLogService() {
		return articleLogService;
	}

	public void setArticleLogService(ArticleLogService articleLogService) {
		this.articleLogService = articleLogService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	public String getStatusAction() {
		return statusAction;
	}

	public void setStatusAction(String statusAction) {
		this.statusAction = statusAction;
	}
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	
	public String getArticleIdOid() {
		return articleIdOid;
	}

	public void setArticleIdOid(String articleIdOid) {
		this.articleIdOid = articleIdOid;
	}

	public List<ArticleLog> getArticleLogs() {
		return articleLogs;
	}

	public void setArticleLogs(List<ArticleLog> articleLogs) {
		this.articleLogs = articleLogs;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	
}
