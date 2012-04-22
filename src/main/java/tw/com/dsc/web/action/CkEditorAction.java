package tw.com.dsc.web.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tw.com.dsc.domain.Attachment;
import tw.com.dsc.service.AttachmentService;

import com.opensymphony.xwork2.ActionSupport;
@Component("ckEditorAction")
@Scope("prototype")
public class CkEditorAction extends ActionSupport implements ServletContextAware {
	private static final Logger logger = LoggerFactory.getLogger(CkEditorAction.class);
	private static final String DEFAULT_PATH = "upload/";
	private static final long serialVersionUID = 8521892056510784666L;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	private String fileUrl;
	private String CKEditorFuncNum;
	
	private ServletContext context;
	
	private String path = DEFAULT_PATH;
	@Autowired
	private AttachmentService attachmentService;
	public String uploadImage() {
		String ctx = this.context.getContextPath();
		try {
			Attachment attachment = this.attachmentService.saveAttchment(ctx+"/"+path, uploadFileName, uploadContentType);
			
			this.fileUrl = attachment.getUri();
			
			String folder = this.context.getRealPath("/")+path;
			File pt = new File(folder);
			if (!pt.exists()) {
				pt.mkdirs();
			}
			FileUtils.copyFile(upload, new File(folder,attachment.getFullName()));
			
		} catch (IOException e) {
			logger.error("File Upload Failed", e);
		}
		return "successImg";
	}
	
	public String uploadFile() {
		return this.uploadImage();
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

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
}
