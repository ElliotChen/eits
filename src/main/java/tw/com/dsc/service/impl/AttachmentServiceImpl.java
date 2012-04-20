package tw.com.dsc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.dao.AttachmentDao;
import tw.com.dsc.domain.Attachment;
import tw.com.dsc.service.AttachmentService;
import tw.com.dsc.util.FileUtils;

@Service("attachmentService")
@Transactional(readOnly=true)
public class AttachmentServiceImpl extends AbstractDomainService<AttachmentDao, Attachment, Long> implements AttachmentService {
	private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);
	@Autowired
	private AttachmentDao dao;

	@Override
	public AttachmentDao getDao() {
		return dao;
	}

	@Override
	public Logger getLogger() {
		return logger;
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
