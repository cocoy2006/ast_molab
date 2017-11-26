package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Subtask;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Subtask entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.Subtask
 * @author MyEclipse Persistence Tools
 */
@Repository
public class SubtaskDao extends BaseDao<Subtask> {
	private static final Logger log = LoggerFactory.getLogger(SubtaskDao.class);
	// property constants
	public static final String TASK_ID = "taskId";
	public static final String PERCENT = "percent";
	public static final String USERS = "users";
	public static final String USERS_DONE = "usersDone";
	public static final String DR_USERS = "drUsers";
	public static final String DR_DONE = "drDone";
	public static final String WR_USERS = "wrUsers";
	public static final String WR_DONE = "wrDone";
	public static final String MR_USERS = "mrUsers";
	public static final String MR_DONE = "mrDone";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String TOTAL = "total";
	public static final String ACTUAL = "actual";
	public static final String RUNNING = "running";
	public static final String PASS = "pass";
	public static final String ERROR = "error";
	public static final String STATE = "state";

	public Subtask findById(java.lang.Integer id) {
		log.debug("getting Subtask instance with id: " + id);
		try {
			Subtask instance = (Subtask) getHibernateTemplate().get(
					"molab.main.java.entity.Subtask", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Subtask> findByExample(Subtask instance) {
		log.debug("finding Subtask instance by example");
		try {
			List<Subtask> results = (List<Subtask>) getHibernateTemplate()
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
		log.debug("finding Subtask instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Subtask as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Subtask> findByTaskId(Object taskId) {
		return findByProperty(TASK_ID, taskId);
	}

	public List<Subtask> findByPercent(Object percent) {
		return findByProperty(PERCENT, percent);
	}

	public List<Subtask> findByUsers(Object users) {
		return findByProperty(USERS, users);
	}

	public List<Subtask> findByUsersDone(Object usersDone) {
		return findByProperty(USERS_DONE, usersDone);
	}

	public List<Subtask> findByDrUsers(Object drUsers) {
		return findByProperty(DR_USERS, drUsers);
	}

	public List<Subtask> findByDrDone(Object drDone) {
		return findByProperty(DR_DONE, drDone);
	}

	public List<Subtask> findByWrUsers(Object wrUsers) {
		return findByProperty(WR_USERS, wrUsers);
	}

	public List<Subtask> findByWrDone(Object wrDone) {
		return findByProperty(WR_DONE, wrDone);
	}

	public List<Subtask> findByMrUsers(Object mrUsers) {
		return findByProperty(MR_USERS, mrUsers);
	}

	public List<Subtask> findByMrDone(Object mrDone) {
		return findByProperty(MR_DONE, mrDone);
	}

	public List<Subtask> findByStartDate(Object startDate) {
		return findByProperty(START_DATE, startDate);
	}

	public List<Subtask> findByEndDate(Object endDate) {
		return findByProperty(END_DATE, endDate);
	}

	public List<Subtask> findByTotal(Object total) {
		return findByProperty(TOTAL, total);
	}

	public List<Subtask> findByActual(Object actual) {
		return findByProperty(ACTUAL, actual);
	}

	public List<Subtask> findByRunning(Object running) {
		return findByProperty(RUNNING, running);
	}

	public List<Subtask> findByPass(Object pass) {
		return findByProperty(PASS, pass);
	}

	public List<Subtask> findByError(Object error) {
		return findByProperty(ERROR, error);
	}

	public List<Subtask> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all Subtask instances");
		try {
			String queryString = "from Subtask";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Subtask merge(Subtask detachedInstance) {
		log.debug("merging Subtask instance");
		try {
			Subtask result = (Subtask) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Subtask instance) {
		log.debug("attaching dirty Subtask instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Subtask instance) {
		log.debug("attaching clean Subtask instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}