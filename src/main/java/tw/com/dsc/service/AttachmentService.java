package tw.com.dsc.service;

import tw.com.dsc.domain.Attachment;

public interface AttachmentService extends BaseDomainService<Attachment, Long> {

	public Attachment saveAttchment(String context, String fileName, String contentType);
}
