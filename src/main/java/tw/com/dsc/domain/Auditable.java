package tw.com.dsc.domain;

import java.util.Date;

public interface Auditable {
	/**
	 * Returns the user who created this entity.
	 *
	 * @return the createdBy
	 */
	String getCreatedAccount();


	/**
	 * Sets the user who created this entity.
	 *
	 * @param createdBy the creating entity to set
	 */
	void setCreatedAccount(final String createdAccount);


	/**
	 * Returns the creation date of the entity.
	 *
	 * @return the createdDate
	 */
	Date getCreatedDate();


	/**
	 * Sets the creation date of the entity.
	 *
	 * @param creationDate the creation date to set
	 */
	void setCreatedDate(final Date creationDate);


	/**
	 * Returns the user who modified the entity lastly.
	 *
	 * @return the lastModifiedBy
	 */
	String getModifiedAccount();


	/**
	 * Sets the user who modified the entity lastly.
	 *
	 * @param lastModifiedBy the last modifying entity to set
	 */
	void setModifiedAccount(final String modifiedAccount);


	/**
	 * Returns the date of the last modification.
	 *
	 * @return the lastModifiedDate
	 */
	Date getModifiedDate();


	/**
	 * Sets the date of the last modification.
	 *
	 * @param lastModifiedDate the date of the last modification to set
	 */
	void setModifiedDate(final Date modifiedDate);
}
