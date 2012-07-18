package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

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
	
	@Column(name="REAL_ABS_PATH", length=400)
	private String realAbsPath;
	
	@Lob
	@Column(name="CONTENT")
	private byte[] content;
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
		return "Attachment [name=" + name + ", extension=" + extension + ", uri=" + uri + ", contentType="
				+ contentType + ", realAbsPath=" + realAbsPath + "]";
	}

	public String getRealAbsPath() {
		return realAbsPath;
	}

	public void setRealAbsPath(String realAbsPath) {
		this.realAbsPath = realAbsPath;
	}

	public String getFullName() {
		if (StringUtils.isEmpty(extension)) {
			return this.oid.toString();
		}
		return this.oid+"."+this.extension;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
}
