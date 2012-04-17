package tw.com.dsc.web.action;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
@Component("ckEditorAction")
@Scope("prototype")
public class CkEditorAction extends ActionSupport implements ServletContextAware {
	private static final long serialVersionUID = 8521892056510784666L;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	private String fileUrl;
	private String CKEditorFuncNum;
	
	private ServletContext context;
	public String uploadImage() {
		
		try {
			String path = this.context.getRealPath("/")+"upload/img/";
			File pt = new File(path);
			if (!pt.exists()) {
				pt.mkdirs();
			}
			FileUtils.copyFile(upload, new File(path,uploadFileName));
			this.fileUrl = "/eits/upload/img/"+uploadFileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "successImg";
	}
	
	public String uploadFile() {
		
		try {
			String path = this.context.getRealPath("/")+"upload/file/";
			File pt = new File(path);
			if (!pt.exists()) {
				pt.mkdirs();
			}
			FileUtils.copyFile(upload, new File(path,uploadFileName));
			this.fileUrl = "/eits/upload/img/"+uploadFileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "successImg";
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

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getCKEditorFuncNum() {
		return CKEditorFuncNum;
	}

	public void setCKEditorFuncNum(String cKEditorFuncNum) {
		CKEditorFuncNum = cKEditorFuncNum;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	
}
