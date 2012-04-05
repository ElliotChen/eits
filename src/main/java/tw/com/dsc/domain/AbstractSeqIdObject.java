package tw.com.dsc.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractSeqIdObject implements Identifiable<Long> {

	private static final long serialVersionUID = 7933938795919651392L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENTITY_SEQ")
	@Column(name = "OID")
	private Long oid;
	
	@Override
	public Long getOid() {
		return this.oid;
	}

	@Override
	public void setOid(Long oid) {
		this.oid = oid;
	}

}

