package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Agentrunner;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Agentrunner entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see molab.main.java.entity.Agentrunner
 * @author MyEclipse Persistence Tools
 */
@Repository
public class AgentrunnerDao extends BaseDao<Agentrunner> {
	private static final Logger log = LoggerFactory
			.getLogger(AgentrunnerDao.class);
	// property constants
	public static final String SUBTASK_ID = "subtaskId";
	public static final String AGENT_ID = "agentId";
	public static final String ISRETENT = "isretent";
	public static final String IMEI = "imei";
	public static final String SN = "sn";
	public static final String MAC = "mac";
	public static final String ANDROIDID = "androidid";
	public static final String STATE = "state";

	public Agentrunner findById(java.lang.Integer id) {
		log.debug("getting Agentrunner instance with id: " + id);
		try {
			Agentrunner instance = (Agentrunner) getHibernateTemplate().get(
					"molab.main.java.entity.Agentrunner", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Agentrunner> findByExample(Agentrunner instance) {
		log.debug("finding Agentrunner instance by example");
		try {
			List<Agentrunner> results = (List<Agentrunner>) getHibernateTemplate()
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
		log.debug("finding Agentrunner instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Agentrunner as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Agentrunner> findBySubtaskId(Object subtaskId) {
		return findByProperty(SUBTASK_ID, subtaskId);
	}

	public List<Agentrunner> findByAgentId(Object agentId) {
		return findByProperty(AGENT_ID, agentId);
	}

	public List<Agentrunner> findByIsretent(Object isretent) {
		return findByProperty(ISRETENT, isretent);
	}

	public List<Agentrunner> findByImei(Object imei) {
		return findByProperty(IMEI, imei);
	}

	public List<Agentrunner> findBySn(Object sn) {
		return findByProperty(SN, sn);
	}

	public List<Agentrunner> findByMac(Object mac) {
		return findByProperty(MAC, mac);
	}
	
	public List<Agentrunner> findByAndroidid(Object androidid) {
		return findByProperty(ANDROIDID, androidid);
	}

	public List<Agentrunner> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findAll() {
		log.debug("finding all Agentrunner instances");
		try {
			String queryString = "from Agentrunner";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Agentrunner merge(Agentrunner detachedInstance) {
		log.debug("merging Agentrunner instance");
		try {
			Agentrunner result = (Agentrunner) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Agentrunner instance) {
		log.debug("attaching dirty Agentrunner instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Agentrunner instance) {
		log.debug("attaching clean Agentrunner instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}