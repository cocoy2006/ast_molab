package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Model;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for Model
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see molab.main.java.entity.Model
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ModelDao extends BaseDao<Model> {
	private static final Logger log = LoggerFactory.getLogger(ModelDao.class);
	// property constants
	public static final String MANUFACTURE_ID = "manufactureId";
	public static final String NAME = "name";
	public static final String OCCUPANCY = "occupancy";
	public static final String STATE = "state";

	public Model findById(java.lang.Integer id) {
		log.debug("getting Model instance with id: " + id);
		try {
			Model instance = (Model) getHibernateTemplate().get(
					"molab.main.java.entity.Model", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Model> findByExample(Model instance) {
		log.debug("finding Model instance by example");
		try {
			List<Model> results = (List<Model>) getHibernateTemplate()
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
		log.debug("finding Model instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Model as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Model> findByManufactureId(Object manufactureId) {
		return findByProperty(MANUFACTURE_ID, manufactureId);
	}

	public List<Model> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Model> findByOccupancy(Object occupancy) {
		return findByProperty(OCCUPANCY, occupancy);
	}

	public List<Model> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all Model instances");
		try {
			String queryString = "from Model";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Model merge(Model detachedInstance) {
		log.debug("merging Model instance");
		try {
			Model result = (Model) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Model instance) {
		log.debug("attaching dirty Model instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Model instance) {
		log.debug("attaching clean Model instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}