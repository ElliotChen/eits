package tw.com.dsc.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

public interface Identifiable<Oid extends Serializable> extends Serializable {
	/**
	 * Returns the id of the entity.
	 *
	 * @return the object id
	 */
	Oid getOid();
	
	void setOid(Oid oid);
}