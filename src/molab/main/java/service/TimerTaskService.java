package molab.main.java.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import molab.main.java.dao.DispatcherDao;
import molab.main.java.dao.ProxyDao;
import molab.main.java.dao.ServerDao;
import molab.main.java.dao.SubtaskDao;
import molab.main.java.dao.TaskDao;
import molab.main.java.dao.UserDao;
import molab.main.java.entity.Dispatcher;
import molab.main.java.entity.Server;
import molab.main.java.entity.Subtask;
import molab.main.java.entity.Task;
import molab.main.java.util.HttpUtil;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;

import org.apache.commons.httpclient.HttpStatus;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimerTaskService {

	private static final Logger log = Logger.getLogger(TimerTaskService.class.getName());
	
	@Autowired
	private DispatcherDao dd;
	
	@Autowired
	private ServerDao sd;
	
	@Autowired
	private SubtaskDao std;
	
	@Autowired
	private TaskDao td;
	
	@Autowired
	private ProxyDao pd;
	
	@Autowired
	private UserDao ud;
	
	public void runDispatcher() {
		List<Dispatcher> dispatchers = dd.findByState(Status.RunningStatus.START.getInt());
		if(dispatchers != null && dispatchers.size() > 0) {
			for(Dispatcher dispatcher : dispatchers) {
				// load task in order to get information of user and application
				Task task = td.findById(dispatcher.getTaskId());
				// fetch apk
				File apk = new File(Molab.getUploadDir() + File.separator + task.getUserId() + File.separator + task.getApplicationId() + ".apk");
				// upload apk to all ast server
				List<Server> servers = sd.findAll();
				if(servers != null && servers.size() > 0) {
					for(Server server : servers) {
						String host = server.getUrl() + "index/upload";
						Map<String, String> params = new HashMap<String, String>();
						params.put("dispatcherId", dispatcher.getId().toString());
						params.put("userId", task.getUserId().toString());
						params.put("filename", task.getApplicationId() + ".apk");
						try {
							int statusCode = HttpUtil.httpFilePost(host, apk, params);
							dispatcher.setCode(statusCode);
							if(statusCode == HttpStatus.SC_OK) { // 200
								dispatcher.setState(Status.RunningStatus.END.getInt());
								dispatcher.setTime(System.currentTimeMillis());
							}
							dd.update(dispatcher);
							log.info("Dispatcher with id " + dispatcher.getId() + " updated success.");
						} catch (FileNotFoundException e) {
							log.severe(e.getMessage());
						}
						
					}
				}
			}
		}
//		log.info(getUploadDir());
	}
	
//	private String getUploadDir() {
//		File thisFile = new File(this.getClass().getResource("/").getPath());
//		return thisFile.getParentFile().getParentFile().getAbsolutePath() + File.separator + "upload";
//	}
	
	public void runSubtask() {
		List<Subtask> subtaskList = findSubtaskList();
		if(subtaskList != null && subtaskList.size() > 0) {
			for(Subtask st : subtaskList) {
				// calculate actual value
				st.setActual(st.getPass() + st.getError());
				// shift state to end
				st.setState(Status.RunningStatus.END.getInt());
				std.update(st);
			}
		}
	}
	
	private List<Subtask> findSubtaskList() {
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Subtask.class);
			dc.add(Restrictions.eq("state", Status.RunningStatus.START.getInt()))
				.add(Restrictions.lt("endDate", Molab.parseDate()));
			return std.findByCriteria(dc);
		} catch (ParseException e) {
			log.severe(e.getMessage());
		}
		return null;
	}

	public void runTask() {
		List<Task> taskList = td.findByState(Status.RunningStatus.START.getInt());
		if(taskList != null && taskList.size() > 0) {
			for(Task task : taskList) {
				List<Subtask> subtaskList = findSubtaskList(task.getId());
				if(subtaskList != null && subtaskList.size() > 0) {
					int actual = 0, pass = 0, error = 0;
					for(Subtask st : subtaskList) {
						actual += st.getActual();
						pass += st.getPass();
						error += st.getError();
					}
					task.setActual(actual);
					task.setPass(pass);
					task.setError(error);
				} else {
					task.setState(Status.RunningStatus.END.getInt());
				}
				td.update(task);
			}
		}
	}
	
	private List<Subtask> findSubtaskList(Integer taskId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Subtask.class);
		dc.add(Restrictions.eq("taskId", taskId))
			.add(Restrictions.eq("state", Status.RunningStatus.END.getInt()));
		return std.findByCriteria(dc);
	}
	
}
