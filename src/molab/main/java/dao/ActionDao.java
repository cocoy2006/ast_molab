package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Action;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Action entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.Action
 * @author MyEclipse Persistence Tools
 */
@Repository
public class ActionDao extends BaseDao<Action> {
	private static final Logger log = LoggerFactory.getLogger(ActionDao.class);
	// property constants
	public static final String TASK_ID = "taskId";
	public static final String TYPE = "type";
	public static final String CONTENT = "content";
	public static final String TIME = "time";
	public static final String STATE = "state";

	public Action findById(java.lang.Integer id) {
		log.debug("getting Action instance with id: " + id);
		try {
			Action instance = (Action) getHibernateTemplate().get(
					"molab.main.java.entity.Action", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Action> findByExample(Action instance) {
		log.debug("finding Action instance by example");
		try {
			List<Action> results = (List<Action>) getHibernateTemplate()
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
		log.debug("finding Action instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Action as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Action> findByTaskId(Object taskId) {
		return findByProperty(TASK_ID, taskId);
	}

	public List<Action> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<Action> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<Action> findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List<Action> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all Action instances");
		try {
			String queryString = "from Action";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Action merge(Action detachedInstance) {
		log.debug("merging Action instance");
		try {
			Action result = (Action) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Action instance) {
		log.debug("attaching dirty Action instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Action instance) {
		log.debug("attaching clean Action instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}