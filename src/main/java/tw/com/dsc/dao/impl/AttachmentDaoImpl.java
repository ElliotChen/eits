package tw.com.dsc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import tw.com.dsc.dao.AttachmentDao;
import tw.com.dsc.domain.Attachment;

@Repository("attachmentDao")
public class AttachmentDaoImpl extends AbstractBaseDao<Attachment, Long> implements AttachmentDao {
	private static final Logger logger = LoggerFactory.getLogger(AttachmentDaoImpl.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

}
