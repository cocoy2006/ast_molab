package molab.main.java.service;

import java.text.ParseException;
import java.util.logging.Logger;

import molab.main.java.component.AssetsComponent;
import molab.main.java.dao.UserDao;
import molab.main.java.entity.User;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private static final Logger log = Logger.getLogger(UserService.class.getName());
	
	@Autowired
	private UserDao dao;
	
	public User findById(int id) {
		return dao.findById(id);
	}
	
	public Object signin(String username, String password) {
		User user = dao.findByUsername(username);
		if(user != null && user.getRole().equals(Status.UserRole.USER.getInt())) {
			if(password.equalsIgnoreCase(user.getPassword())) {
				return user;
			} else {
				return Status.UserStatus.PASSWORD_NOT_MATCH.getInt();
			}
		} else {
			return Status.UserStatus.NOT_EXIST.getInt();
		}
	}
	
	public int check(String username) {
		User user = dao.findByUsername(username);
		if(user == null) {
			return Status.Common.SUCCESS.getInt();
		} else {
			return Status.UserStatus.EXIST.getInt();
		}
	}
	
	public int signup(String username, String password, String email) {
		User user = dao.findByUsername(username);
		if(user == null) {
			user = new User(username, password, email, Status.DEFAULT_ASSETS,
					Status.UserStatus.NORMAL.getInt(),
					Status.UserRole.USER.getInt());
			int id = (Integer) dao.save(user);
			return id;
		} else {
			return Status.UserStatus.EXIST.getInt();
		}
	}
	
	public AssetsComponent checkAssets(int id, int users, 
			int dayRetention, int weekRetention, int monthRetention, 
			String startDay, String endDay) throws ParseException {
		AssetsComponent ac = new AssetsComponent();
		if(Molab.diffDays(startDay, endDay) < 0) {
			ac.setMessage(" 日期不合法 ");
		} else {
			User user = dao.findById(id);
			if(user != null) {
				int total = Molab.total(Molab.initStMap(users, dayRetention,
						weekRetention, monthRetention, startDay, endDay));
				ac.setTotal(total);
				int assets = user.getAssets();
				ac.setAssets(assets);
				if(assets < total) {
					ac.setState(Status.Common.ASSETS_NOT_ENOUGH.getInt());
				} else {
					ac.setState(Status.Common.SUCCESS.getInt());
				}
			}
		}
		return ac;
	}
	
}
