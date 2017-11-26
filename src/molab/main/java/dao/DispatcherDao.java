package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Dispatcher;

import org.hibernate.LockMode;
import org.hibernate.Query;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Dispatcher entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.Dispatcher
 * @author MyEclipse Persistence Tools
 */
@Repository
public class DispatcherDao extends BaseDao<Dispatcher> {
	private static final Logger log = LoggerFactory
			.getLogger(DispatcherDao.class);
	// property constants
	public static final String TASK_ID = "taskId";
	public static final String SERVER_ID = "serverId";
	public static final String RESULT = "result";
	public static final String CODE = "code";
	public static final String STATE = "state";
	public static final String TIME = "time";

	public Dispatcher findById(java.lang.Integer id) {
		log.debug("getting Dispatcher instance with id: " + id);
		try {
			Dispatcher instance = (Dispatcher) getHibernateTemplate().get(
					"molab.main.java.entity.Dispatcher", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Dispatcher> findByExample(Dispatcher instance) {
		log.debug("finding Dispatcher instance by example");
		try {
			List<Dispatcher> results = (List<Dispatcher>) getHibernateTemplate()
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
		log.debug("finding Dispatcher instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Dispatcher as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Dispatcher> findByTaskId(Object taskId) {
		return findByProperty(TASK_ID, taskId);
	}

	public List<Dispatcher> findByServerId(Object serverId) {
		return findByProperty(SERVER_ID, serverId);
	}

	public List<Dispatcher> findByResult(Object result) {
		return findByProperty(RESULT, result);
	}

	public List<Dispatcher> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<Dispatcher> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<Dispatcher> findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List findAll() {
		log.debug("finding all Dispatcher instances");
		try {
			String queryString = "from Dispatcher";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Dispatcher merge(Dispatcher detachedInstance) {
		log.debug("merging Dispatcher instance");
		try {
			Dispatcher result = (Dispatcher) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Dispatcher instance) {
		log.debug("attaching dirty Dispatcher instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Dispatcher instance) {
		log.debug("attaching clean Dispatcher instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}