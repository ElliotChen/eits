package tw.com.dsc.service;

import java.io.File;

import tw.com.dsc.domain.Attachment;

public interface AttachmentService extends BaseDomainService<Attachment, Long> {

	public Attachment saveAttchment(String context, String fileName, String contentType);
	
	public Attachment uploadImage(String contextPath, File file,String fileName, String contentType);
	public Attachment uploadFile(String contextPath, File file,String fileName, String contentType);
	public Attachment uploadFirmware(String contextPath, File file,String fileName, String contentType);
}
