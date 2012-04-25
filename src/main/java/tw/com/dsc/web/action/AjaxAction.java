package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tw.com.dsc.to.SelectOption;

import com.opensymphony.xwork2.ActionSupport;

@Component("ajaxAction")
public class AjaxAction extends ActionSupport {
	private static final Logger logger = LoggerFactory.getLogger(AjaxAction.class);
	private static final long serialVersionUID = 8897370674931995489L;
	private List<SelectOption> options;
	
	private String series;
	public String ajaxModels() {
		
		options = new ArrayList<SelectOption>();
		if ("pa".equals(series)) {
			options.add(new SelectOption("ma", "ModelA"));
			options.add(new SelectOption("mb", "ModelB"));
			options.add(new SelectOption("mc", "ModelC"));
		} else if ("pb".equals(series)){
			options.add(new SelectOption("md", "ModelD"));
			options.add(new SelectOption("me", "ModelE"));
			options.add(new SelectOption("mf", "ModelF"));
		} else {
			options.add(new SelectOption("", "-----"));
		}
		
		return "jsonModels";
	}
	public List<SelectOption> getOptions() {
		return options;
	}
	public void setOptions(List<SelectOption> options) {
		this.options = options;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	
	
}
