package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.Server;

import org.hibernate.LockMode;
import org.hibernate.Query;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Server entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see molab.main.java.entity.Server
 * @author MyEclipse Persistence Tools
 */

@Repository
public class ServerDao extends BaseDao<Server> {
	private static final Logger log = LoggerFactory.getLogger(ServerDao.class);
	// property constants
	public static final String URL = "url";

	public Server findById(java.lang.Integer id) {
		log.debug("getting Server instance with id: " + id);
		try {
			Server instance = (Server) getHibernateTemplate().get(
					"molab.main.java.entity.Server", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Server> findByExample(Server instance) {
		log.debug("finding Server instance by example");
		try {
			List<Server> results = (List<Server>) getHibernateTemplate()
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
		log.debug("finding Server instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Server as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Server> findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	public List findAll() {
		log.debug("finding all Server instances");
		try {
			String queryString = "from Server";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Server merge(Server detachedInstance) {
		log.debug("merging Server instance");
		try {
			Server result = (Server) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Server instance) {
		log.debug("attaching dirty Server instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Server instance) {
		log.debug("attaching clean Server instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}