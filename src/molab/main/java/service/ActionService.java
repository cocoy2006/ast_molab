package molab.main.java.service;

import java.util.logging.Logger;

import molab.main.java.dao.ActionDao;
import molab.main.java.entity.Action;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

	private static final Logger log = Logger.getLogger(ActionService.class.getName());
	
	@Autowired
	private ActionDao dao;
	
	public int offline(int id) {
		Action action = dao.findById(id);
		if(action != null) {
			action.setState(Status.Common.FALSE.getInt());
			dao.update(action);
			log.info("Action with id " + action.getId() + " offline.");
			return Status.Common.SUCCESS.getInt();
		}
		return Status.Common.ERROR.getInt();
	}
	
}
