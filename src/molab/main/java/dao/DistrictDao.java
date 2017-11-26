package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.District;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * District entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.District
 * @author MyEclipse Persistence Tools
 */
@Repository
public class DistrictDao extends BaseDao<District> {
	private static final Logger log = LoggerFactory
			.getLogger(DistrictDao.class);
	// property constants
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String SHORT_ = "short_";

	public District findById(java.lang.Integer id) {
		log.debug("getting District instance with id: " + id);
		try {
			District instance = (District) getHibernateTemplate().get(
					"molab.main.java.entity.District", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<District> findByExample(District instance) {
		log.debug("finding District instance by example");
		try {
			List<District> results = (List<District>) getHibernateTemplate()
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
		log.debug("finding District instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from District as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<District> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<District> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<District> findByShort_(Object short_) {
		return findByProperty(SHORT_, short_);
	}

	public List findAll() {
		log.debug("finding all District instances");
		try {
			String queryString = "from District";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public District merge(District detachedInstance) {
		log.debug("merging District instance");
		try {
			District result = (District) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(District instance) {
		log.debug("attaching dirty District instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(District instance) {
		log.debug("attaching clean District instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}