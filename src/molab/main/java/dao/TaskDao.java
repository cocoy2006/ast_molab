package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Task;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for Task
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see molab.main.java.entity.Task
 * @author MyEclipse Persistence Tools
 */
@Repository
public class TaskDao extends BaseDao<Task> {
	private static final Logger log = LoggerFactory.getLogger(TaskDao.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String APPLICATION_ID = "applicationId";
	public static final String USERS = "users";
	public static final String DAY_RETENTION = "dayRetention";
	public static final String WEEK_RETENTION = "weekRetention";
	public static final String MONTH_RETENTION = "monthRetention";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String TOTAL = "total";
	public static final String ACTUAL = "actual";
	public static final String PASS = "pass";
	public static final String ERROR = "error";
	public static final String TIME = "time";
	public static final String STATE = "state";

	public Task findById(java.lang.Integer id) {
		log.debug("getting Task instance with id: " + id);
		try {
			Task instance = (Task) getHibernateTemplate().get(
					"molab.main.java.entity.Task", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Task> findByExample(Task instance) {
		log.debug("finding Task instance by example");
		try {
			List<Task> results = (List<Task>) getHibernateTemplate()
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
		log.debug("finding Task instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Task as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Task> findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List<Task> findByApplicationId(Object applicationId) {
		return findByProperty(APPLICATION_ID, applicationId);
	}

	public List<Task> findByUsers(Object users) {
		return findByProperty(USERS, users);
	}

	public List<Task> findByDayRetention(Object dayRetention) {
		return findByProperty(DAY_RETENTION, dayRetention);
	}

	public List<Task> findByWeekRetention(Object weekRetention) {
		return findByProperty(WEEK_RETENTION, weekRetention);
	}

	public List<Task> findByMonthRetention(Object monthRetention) {
		return findByProperty(MONTH_RETENTION, monthRetention);
	}

	public List<Task> findByStartDate(Object startDate) {
		return findByProperty(START_DATE, startDate);
	}

	public List<Task> findByEndDate(Object endDate) {
		return findByProperty(END_DATE, endDate);
	}

	public List<Task> findByTotal(Object total) {
		return findByProperty(TOTAL, total);
	}

	public List<Task> findByActual(Object actual) {
		return findByProperty(ACTUAL, actual);
	}

	public List<Task> findByPass(Object pass) {
		return findByProperty(PASS, pass);
	}

	public List<Task> findByError(Object error) {
		return findByProperty(ERROR, error);
	}

	public List<Task> findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List<Task> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all Task instances");
		try {
			String queryString = "from Task";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Task merge(Task detachedInstance) {
		log.debug("merging Task instance");
		try {
			Task result = (Task) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Task instance) {
		log.debug("attaching dirty Task instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Task instance) {
		log.debug("attaching clean Task instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}