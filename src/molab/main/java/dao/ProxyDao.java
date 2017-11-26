package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Proxy;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Proxy entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.Proxy
 * @author MyEclipse Persistence Tools
 */

@Repository
public class ProxyDao extends BaseDao<Proxy> {
	private static final Logger log = LoggerFactory.getLogger(ProxyDao.class);
	// property constants
	public static final String IP = "ip";
	public static final String PORT = "port";
	public static final String DISTRICT_CODE = "districtCode";
	public static final String DISTRICT_NAME = "districtName";
	public static final String ISUSED = "isused";
	public static final String TIME = "time";

	public Proxy findById(java.lang.Integer id) {
		log.debug("getting Proxy instance with id: " + id);
		try {
			Proxy instance = (Proxy) getHibernateTemplate().get(
					"molab.main.java.entity.Proxy", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Proxy> findByExample(Proxy instance) {
		log.debug("finding Proxy instance by example");
		try {
			List<Proxy> results = (List<Proxy>) getHibernateTemplate()
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
		log.debug("finding Proxy instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Proxy as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Proxy> findByIp(Object ip) {
		return findByProperty(IP, ip);
	}
	
	public List<Proxy> findByPort(Object port) {
		return findByProperty(PORT, port);
	}

	public List<Proxy> findByDistrictCode(Object districtCode) {
		return findByProperty(DISTRICT_CODE, districtCode);
	}

	public List<Proxy> findByDistrictName(Object districtName) {
		return findByProperty(DISTRICT_NAME, districtName);
	}
	
	public List<Proxy> findByIsused(Object isused) {
		return findByProperty(ISUSED, isused);
	}
	
	public List<Proxy> findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List findAll() {
		log.debug("finding all Proxy instances");
		try {
			String queryString = "from Proxy";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Proxy merge(Proxy detachedInstance) {
		log.debug("merging Proxy instance");
		try {
			Proxy result = (Proxy) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Proxy instance) {
		log.debug("attaching dirty Proxy instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Proxy instance) {
		log.debug("attaching clean Proxy instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}