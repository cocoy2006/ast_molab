package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Installation;

import org.hibernate.LockMode;
import org.hibernate.Query;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Installation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see molab.main.java.entity.Installation
 * @author MyEclipse Persistence Tools
 */
@Repository
public class InstallationDao extends BaseDao<Installation> {
	private static final Logger log = LoggerFactory
			.getLogger(InstallationDao.class);
	// property constants
	public static final String DISPATCHER_ID = "dispatcherId";
	public static final String SN = "sn";
	public static final String RESULT = "result";
	public static final String STATE = "state";
	public static final String TIME = "time";

	public Installation findById(java.lang.Integer id) {
		log.debug("getting Installation instance with id: " + id);
		try {
			Installation instance = (Installation) getHibernateTemplate().get(
					"molab.main.java.entity.Installation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Installation> findByExample(Installation instance) {
		log.debug("finding Installation instance by example");
		try {
			List<Installation> results = (List<Installation>) getHibernateTemplate()
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
		log.debug("finding Installation instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Installation as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Installation> findByDispatcherId(Object dispatcherId) {
		return findByProperty(DISPATCHER_ID, dispatcherId);
	}
	
	public List<Installation> findBySn(Object sn) {
		return findByProperty(SN, sn);
	}

	public List<Installation> findByResult(Object result) {
		return findByProperty(RESULT, result);
	}

	public List<Installation> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<Installation> findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List findAll() {
		log.debug("finding all Installation instances");
		try {
			String queryString = "from Installation";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Installation merge(Installation detachedInstance) {
		log.debug("merging Installation instance");
		try {
			Installation result = (Installation) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Installation instance) {
		log.debug("attaching dirty Installation instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Installation instance) {
		log.debug("attaching clean Installation instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}