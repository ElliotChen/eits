package tw.com.dsc.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tw.com.dsc.domain.Article;

public class ExportInfo implements Serializable {

	private static final long serialVersionUID = -9044746195364529981L;

	private String account;
	private String name;
	private List<Article> articles = new ArrayList<Article>();
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return articles.size();
	}
}
