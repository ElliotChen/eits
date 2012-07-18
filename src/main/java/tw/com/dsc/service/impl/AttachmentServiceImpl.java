package tw.com.dsc.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.AttachmentDao;
import tw.com.dsc.domain.Attachment;
import tw.com.dsc.service.AttachmentService;
import tw.com.dsc.util.DateUtils;
import tw.com.dsc.util.FileUtils;

/**
 * Real Path = realParentPath + applicationContext + typeFolder(image, file, firmware) + attachmentOid + extension;
 * 
 * @author elliot
 *
 */
@Service("attachmentService")
@Transactional(readOnly=true)
public class AttachmentServiceImpl extends AbstractDomainService<AttachmentDao, Attachment, Long> implements AttachmentService {
	private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);
	@Autowired
	private AttachmentDao dao;

	@Value("${upload.image}")
	private String imageFolder;
	@Value("${upload.file}")
	private String fileFolder;
	@Value("${upload.firmware}")
	private String firmwareFolder;
	@Value("${upload.realParent}")
	private String realParentPath;
	@Value("${upload.blob}")
	private Boolean useBlob;
	
	@Override
	public AttachmentDao getDao() {
		return dao;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Transactional(readOnly=false)
	public Attachment uploadImage(String contextPath, File file, String fileName, String contentType) {
		return this.upload(contextPath, file, fileName, contentType, imageFolder);
	}
	
	@Override
	@Transactional(readOnly=false)
	public Attachment uploadFile(String contextPath, File file, String fileName, String contentType) {
		return this.upload(contextPath, file, fileName, contentType, fileFolder);
	}
	
	@Override
	@Transactional(readOnly=false)
	public Attachment uploadFirmware(String contextPath, File file, String fileName, String contentType) {
		return this.upload(contextPath, file, fileName, contentType, firmwareFolder);
	}
	
	protected Attachment upload(String contextPath, File file, String fileName, String contentType, String typeFolder) {
		Attachment attachment = new Attachment();
		attachment.setContentType(contentType);
		attachment.setName(fileName);
		attachment.setExtension(FileUtils.getExtension(fileName));
		this.dao.create(attachment);
		
		String subPath = contextPath.endsWith("/")?contextPath:contextPath+"/";
		String tf = typeFolder.endsWith("/")?typeFolder:typeFolder+"/";
		if (tf.startsWith("/")) {
			tf = tf.substring(1, tf.length());
		}
		subPath += (tf+DateUtils.formatYearMonth(new Date()));
		
		String destPath = realParentPath+subPath;
		File destFolder = new File(destPath);
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}
		
		File dest = new File(destPath, attachment.getFullName());
		
		if (this.useBlob) {
			attachment.setUri(contextPath+"/searchArticle!viewBlob.action?attOid="+attachment.getOid());
		} else {
			attachment.setUri(subPath + "/" + attachment.getFullName());
		}
		
		attachment.setRealAbsPath(dest.getAbsolutePath());
		try {
			org.apache.commons.io.FileUtils.moveFile(file, dest);
		} catch (IOException e) {
			logger.error("Move File Failed", e);
		}
		
		try {
			attachment.setContent(org.apache.commons.io.FileUtils.readFileToByteArray(dest));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.dao.update(attachment);
		return attachment;
	}
	
	@Transactional(readOnly=false)
	public Attachment saveAttchment(String contextPath, String fileName, String contentType) {
		Attachment attachment = new Attachment();
		attachment.setContentType(contentType);
		attachment.setName(fileName);
		attachment.setExtension(FileUtils.getExtension(fileName));
		this.dao.create(attachment);
		
		String uri = contextPath;
		if (!uri.endsWith("/")) {
			uri += "/";
		}
		uri += attachment.getFullName();
		
		attachment.setUri(uri);
		
		this.dao.update(attachment);
		logger.debug("Save Attachment as {}",attachment);
		return attachment;
	}

}
