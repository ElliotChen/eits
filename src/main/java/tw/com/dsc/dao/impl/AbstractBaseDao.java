package tw.com.dsc.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import tw.com.dsc.domain.Auditable;
import tw.com.dsc.domain.Identifiable;
import tw.com.dsc.domain.support.Condition;
import tw.com.dsc.domain.support.LikeMode;
import tw.com.dsc.domain.support.Page;
import tw.com.dsc.util.ThreadLocalHolder;

public abstract class AbstractBaseDao<T extends Identifiable<Oid>, Oid extends Serializable> {
	protected Class<T> domainClass;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public AbstractBaseDao() {
		this.domainClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getDomainClass() {
		return domainClass;
	}

	public void setDomainClass(Class<T> domainClass) {
		this.domainClass = domainClass;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public T findByOid(Oid oid) {
		return (T) this.sessionFactory.getCurrentSession().get(this.domainClass, oid);
	}

	public void create(T entity) {
		this.preCreate(entity);
		this.sessionFactory.getCurrentSession().save(entity);
	}

	public void update(T entity) {
		this.preUpdate(entity);
		this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	public void delete(T entity) {
		this.preDelete(entity);
		this.sessionFactory.getCurrentSession().delete(entity);
	}
	
	public void merge(T entity) {
		this.preUpdate(entity);
		this.sessionFactory.getCurrentSession().merge(entity);
	}
	
	public List<T> listAll() {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(domainClass);
		return (List<T>) criteria.list();
	}

	public void saveOrUpdate(T entity) {
		this.preUpdate(entity);
		this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	protected Criteria createCriteria(T example, List<Condition> conditions, LikeMode likeMode) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(domainClass);
		if (null != example) {
			MatchMode matchMode = this.getMatchMode(likeMode);
			criteria.add(createExample(example, matchMode));
			if (null != example.getOid()) {
				if (LikeMode.ANYWHERE == likeMode) {
					criteria.add(Restrictions.like("oid", "%"+example.getOid()+"%").ignoreCase());
				} else {
					criteria.add(Restrictions.idEq(example.getOid()));
				}
			}
			this.addConditions(criteria, conditions);
			this.postCreateCriteria(criteria, example, matchMode);
		}
		return criteria;
	}

	protected Example createExample(T example, MatchMode matchMode) {
		Example criteriaExample = Example.create(example).ignoreCase();
		if (null != matchMode) {
			criteriaExample.enableLike(matchMode);
		}
		return criteriaExample;
	}

	protected void setOrderToCriteria(Criteria criteria, String[] ascProperties, String[] descProperties) {
		if (null != ascProperties && 0 < ascProperties.length) {
			for (String ascProperty : ascProperties) {
				criteria.addOrder(Order.asc(ascProperty));
			}
		}
		if (null != descProperties && 0 < descProperties.length) {
			for (String descProperty : descProperties) {
				criteria.addOrder(Order.desc(descProperty));
			}
		}
	}

	protected Integer countByCriteria(Criteria criteria) {
		Projection projection = ((CriteriaImpl) criteria).getProjection();
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(projection);

		if (null == projection) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		return new Integer(totalCount.intValue());
	}

	public List<T> listByExample(final T example) {
		return this.listByExample(example, null, null, null, null);
	}

	public List<T> listByExample(final T example, List<Condition> conditions, LikeMode mode, String[] ascOrders,
			String[] descOrders) {
		Criteria criteria = this.createCriteria(example, conditions, mode);
		this.setOrderToCriteria(criteria, ascOrders, descOrders);
		List<T> result = criteria.list();
		return result;
	}

	public Page<T> listByPage(final Page<T> page) {

		Criteria criteria = this.createCriteria(page.getExample(), page.getConditions(), page.getLikeMode());

		Integer totalCount = this.countByCriteria(criteria);
		page.setTotalCount(totalCount);

		if (1 > totalCount) {
			page.getResult().clear();
			// TODO: reset pageNo?
		} else {
			this.setOrderToCriteria(criteria, page.getAscOrders(), page.getDescOrders());
			List<T> result = (criteria.setFirstResult(page.getCurrentIndex().intValue()).setMaxResults(page.getPageSize())).list();
			page.setResult(result);
		}

		return page;
	}

	/**
	 * Please override this method for customize query.
	 * 
	 * @param criteria
	 * @param example
	 */
	protected void postCreateCriteria(Criteria criteria, T example, MatchMode matchMode) {
		// does nothing.
	}

	protected void addConditions(Criteria criteria, List<Condition> conditions) {
		if (null == criteria || null == conditions || conditions.isEmpty()) {
			return;
		}

		for (Condition cond : conditions) {
			Criterion criterion = CriterionHelper.parse(cond);
			if (null != criterion) {
				criteria.add(criterion);
			}
		}
	}

	protected MatchMode getMatchMode(LikeMode likeMode) {
		if (null == likeMode) {
			return MatchMode.EXACT;
		}
		MatchMode result = null;
		switch (likeMode) {
		case START:
			result = MatchMode.START;
			break;
		case END:
			result = MatchMode.END;
			break;
		case ANYWHERE:
			result = MatchMode.ANYWHERE;
			break;
		default:
			result = MatchMode.EXACT;
		}
		return result;
	}
	
	public abstract Logger getLogger();
	
	protected void preCreate(T entity) {
		if (entity instanceof Auditable) {
			Auditable audit = (Auditable) entity;
			audit.setCreatedAccount(ThreadLocalHolder.getOperator().getAccount());
			audit.setCreatedDate(new Date());
		}
	}

	protected void preUpdate(T entity) {
		if (entity instanceof Auditable) {
			Auditable audit = (Auditable) entity;
			audit.setModifiedAccount(ThreadLocalHolder.getOperator().getAccount());
			audit.setModifiedDate(new Date());
		}
	}
	
	protected void preDelete(T entity) {
		this.getLogger().info("Delete Entity[{}]", entity);
	}
}
