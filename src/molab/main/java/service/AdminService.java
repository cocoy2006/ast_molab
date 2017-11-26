package molab.main.java.service;

import java.util.List;
import java.util.logging.Logger;

import molab.main.java.dao.UserDao;
import molab.main.java.entity.User;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	private static final Logger log = Logger.getLogger(AdminService.class.getName());
	
	@Autowired
	private UserDao dao;
	
	public Object signin(String username, String password) {
		User user = dao.findByUsername(username);
		if(user != null && user.getRole().equals(Status.UserRole.ADMIN.getInt())) {
			if(password.equalsIgnoreCase(user.getPassword())) {
				return user;
			} else {
				return Status.UserStatus.PASSWORD_NOT_MATCH.getInt();
			}
		} else {
			return Status.UserStatus.NOT_EXIST.getInt();
		}
	}
	
	public List<User> findUsers() {
		return dao.findByRole(Status.UserRole.USER.getInt());
	}
	
	public void recharge(int userId, float amount) {
		int assets = new Float(amount * 100).intValue();
		User user = dao.findById(userId);
		if(user != null) {
			user.setAssets(user.getAssets() + assets);
			dao.update(user);
		}
	}
	
}
