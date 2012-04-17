package tw.com.dsc.web.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
@Component("ckEditorAction")
@Scope("prototype")
public class CkEditorAction extends ActionSupport {
	private static final long serialVersionUID = 8521892056510784666L;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	private String fileUrl;
	private String CKEditorFuncNum;
	public String uploadImage() {
		
		try {
			FileUtils.copyFile(upload, new File("/Users/elliot/Documents/eclipse/dsc/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/eits/upload/img",uploadFileName));
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
	
}
