package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_EITS_ATTACHMENT")
@SequenceGenerator(name="ENTITY_SEQ", sequenceName="SEQ_EITS_ATTACHMENT", allocationSize=1)
public class Attachment extends AbstractSeqIdObject {

	private static final long serialVersionUID = -9113460869516359766L;

	@Column(name="NAME", length=100)
	private String name;
	
	@Column(name="EXTENSION", length=10)
	private String extension;
	
	@Column(name="URI", length=255)
	private String uri;
	
	@Column(name="CONTENT_TYPE", length=20)
	private String contentType;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "Attachment [name=" + name + ", extension=" + extension + ", uri=" + uri + ", oid=" + oid + "]";
	}
	
	public String getFullName() {
		return this.oid+this.extension;
	}
}
