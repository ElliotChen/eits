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
	private static final long serialVersionUID = 8521892056510784666L;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	private String fileUrl;
	private String CKEditorFuncNum;
	
	private ServletContext context;
	
	@Autowired
	private AttachmentService attachmentService;
	
	public String uploadImage() {
		logger.debug("Upload Image File[{}]", uploadFileName);
		Attachment attachment = this.attachmentService.uploadImage(this.context.getContextPath()+"/", upload, uploadFileName, uploadContentType);
		if (null != attachment) {
			this.fileUrl = attachment.getUri();
		}
		return "successImg";
	}
	
	public String uploadFile() {
		logger.debug("Upload File[{}]", uploadFileName);
		Attachment attachment = this.attachmentService.uploadFile(this.context.getContextPath()+"/", upload, uploadFileName, uploadContentType);
		if (null != attachment) {
			this.fileUrl = attachment.getUri();
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

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
}
