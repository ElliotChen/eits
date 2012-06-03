package tw.com.dsc.web.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.ProductModel;
import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.SelectOption;

import com.opensymphony.xwork2.ActionSupport;

@Component("ajaxAction")
public class AjaxAction extends ActionSupport {
	private static final Logger logger = LoggerFactory.getLogger(AjaxAction.class);
	private static final long serialVersionUID = 8897370674931995489L;
	private List<SelectOption> options;
	@Autowired
	private SystemService systemService;
	private String exSeries;
	public String ajaxModels() {
		
		options = new ArrayList<SelectOption>();
		options.add(new SelectOption("", "-----"));
		List<ProductModel> models = systemService.listModels(exSeries);
		for (ProductModel model : models) {
			options.add(new SelectOption(model.getName(), model.getName()));
		}
		
		return "jsonModels";
	}
	public List<SelectOption> getOptions() {
		return options;
	}
	public void setOptions(List<SelectOption> options) {
		this.options = options;
	}
	public String getExSeries() {
		return exSeries;
	}
	public void setExSeries(String exSeries) {
		this.exSeries = exSeries;
	}
	
}
