package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Manufacture;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Manufacture entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see molab.main.java.entity.Manufacture
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ManufactureDao extends BaseDao<Manufacture> {
	private static final Logger log = LoggerFactory
			.getLogger(ManufactureDao.class);
	// property constants
	public static final String NAME = "name";
	public static final String STATE = "state";

	public Manufacture findById(java.lang.Integer id) {
		log.debug("getting Manufacture instance with id: " + id);
		try {
			Manufacture instance = (Manufacture) getHibernateTemplate().get(
					"molab.main.java.entity.Manufacture", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Manufacture> findByExample(Manufacture instance) {
		log.debug("finding Manufacture instance by example");
		try {
			List<Manufacture> results = (List<Manufacture>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Manufacture instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Manufacture as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Manufacture> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Manufacture> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all Manufacture instances");
		try {
			String queryString = "from Manufacture";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Manufacture merge(Manufacture detachedInstance) {
		log.debug("merging Manufacture instance");
		try {
			Manufacture result = (Manufacture) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Manufacture instance) {
		log.debug("attaching dirty Manufacture instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Manufacture instance) {
		log.debug("attaching clean Manufacture instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}