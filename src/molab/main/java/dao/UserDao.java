package molab.main.java.dao;

import java.util.List;

import molab.main.java.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see molab.main.java.entity.User
 * @author MyEclipse Persistence Tools
 */

@Repository
public class UserDao extends BaseDao<User> {
	
	private static final Logger log = LoggerFactory.getLogger(UserDao.class);
	
	public static final String USERNAME = "username";
	public static final String NICKNAME = "nickname";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String PHONE = "phone";
	public static final String LEFTTIME = "lefttime";
	public static final String STATE = "state";
	public static final String ROLE = "role";
	
	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get(
					"molab.main.java.entity.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<User> findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List<User> results = (List<User>) getHibernateTemplate()
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
		log.debug("finding User instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public User findByUsername(Object username) {
		List<User> users = findByProperty(USERNAME, username);
		if(users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public List<User> findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List<User> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<User> findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List<User> findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}

	public List<User> findByLefttime(Object lefttime) {
		return findByProperty(LEFTTIME, lefttime);
	}

	public List<User> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<User> findByRole(Object role) {
		return findByProperty(ROLE, role);
	}

	public List<User> findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public static UserDao getFromApplicationContext(ApplicationContext ctx) {
		return (UserDao) ctx.getBean("UserDao");
	}

}