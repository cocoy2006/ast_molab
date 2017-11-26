package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Agent;

import org.hibernate.LockMode;
import org.hibernate.Query;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for Agent
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see molab.main.java.entity.Agent
 * @author MyEclipse Persistence Tools
 */
@Repository
public class AgentDao extends BaseDao<Agent> {
	private static final Logger log = LoggerFactory.getLogger(AgentDao.class);
	// property constants
	public static final String USES = "uses";
	public static final String TIME = "time";
	public static final String LASTTIME = "lasttime";

	public Agent findById(java.lang.Integer id) {
		log.debug("getting Agent instance with id: " + id);
		try {
			Agent instance = (Agent) getHibernateTemplate().get(
					"molab.main.java.entity.Agent", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Agent> findByExample(Agent instance) {
		log.debug("finding Agent instance by example");
		try {
			List<Agent> results = (List<Agent>) getHibernateTemplate()
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
		log.debug("finding Agent instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Agent as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Agent> findByUses(Object uses) {
		return findByProperty(USES, uses);
	}

	public List<Agent> findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List<Agent> findByLasttime(Object lasttime) {
		return findByProperty(LASTTIME, lasttime);
	}

	public List findAll() {
		log.debug("finding all Agent instances");
		try {
			String queryString = "from Agent";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Agent merge(Agent detachedInstance) {
		log.debug("merging Agent instance");
		try {
			Agent result = (Agent) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Agent instance) {
		log.debug("attaching dirty Agent instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Agent instance) {
		log.debug("attaching clean Agent instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}