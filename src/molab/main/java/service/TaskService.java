package molab.main.java.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import molab.main.java.component.TaskComponent;
import molab.main.java.dao.ActionDao;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.DispatcherDao;
import molab.main.java.dao.ServerDao;
import molab.main.java.dao.SubtaskDao;
import molab.main.java.dao.TaskDao;
import molab.main.java.dao.UserDao;
import molab.main.java.entity.Action;
import molab.main.java.entity.Application;
import molab.main.java.entity.Dispatcher;
import molab.main.java.entity.Server;
import molab.main.java.entity.Subtask;
import molab.main.java.entity.Task;
import molab.main.java.entity.User;
import molab.main.java.util.Molab;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service
public class TaskService {

	private static final Logger log = Logger.getLogger(TaskService.class.getName());
	
	@Autowired
	private ActionDao acd;
	
	@Autowired
	private ApplicationDao apd;
	
	@Autowired
	private DispatcherDao dd;
	
	@Autowired
	private ServerDao sd;
	
	@Autowired
	private SubtaskDao std;
	
	@Autowired
	private TaskDao dao;
	
	@Autowired
	private UserDao ud;
	
	public List<TaskComponent> findAll(User user) {
		List<Task> tasks = null;
		if(user != null) {
			tasks = dao.findByUserId(user.getId());
		} else {
			tasks = dao.findAll();
		}
		if(tasks != null && tasks.size() > 0) {
			List<TaskComponent> tcList = new ArrayList<TaskComponent>();
			for(int i = tasks.size() - 1; i >= 0; i--) {
				Task task = tasks.get(i);
				TaskComponent tc = new TaskComponent();
				tc.setTask(task);
				tc.setStartdate(new Date(task.getStartDate()));
				tc.setEnddate(new Date(task.getEndDate()));
				tc.setTime(new Date(task.getTime()));
				// find application
				Application application = apd.findById(task.getApplicationId());
				if(application != null) {
					tc.setApplication(application);
				}
				tcList.add(tc);
			}
			return tcList;
		}
		return null;
	}
	
	public int build(CommonsMultipartFile file, String path, int userId, int users, 
			int conversion, int dayRetention, int weekRetention, int monthRetention,
			String district, String startDay, String endDay, Integer[][] periodArray, Integer pid)
			throws IOException, ParseException {
		User user = ud.findById(userId);
		if(user != null) {
			// check assets first
			Map<Long, Subtask> stMap = Molab.initStMap(users, dayRetention, weekRetention, monthRetention, startDay, endDay);
			int total = Molab.total(stMap);
			int assets = user.getAssets();
			if(assets < total) {
				return Status.Common.ASSETS_NOT_ENOUGH.getInt();
			} else { // assets is enough
				// copy file
				String fileNameFull = path + File.separator + file.getOriginalFilename();
				File destFile = new File(fileNameFull);
				FileCopyUtils.copy(file.getBytes(), destFile);
				// build application
				Application application = Molab.parse(destFile);
				if (application != null) {
					application.setPid(pid);
					int applicationId = (Integer) apd.save(application);
					log.info("Application with id " + applicationId + " saved success.");
					// build task
					int taskId = saveTask(userId, applicationId, users, conversion,
							dayRetention, weekRetention, monthRetention,
							district, startDay, endDay, total);
					// build subtasks
					List<Subtask> stList = Molab.buildStList(stMap, taskId, conversion, periodArray);
					for(Subtask st : stList) {
						saveSubtask(st);
					}
					// update assets
					int newAssets = user.getAssets() - total;
					user.setAssets(newAssets);
					ud.update(user);
					log.info("User with id " + user.getId() + " updated success. New assets is " + newAssets);
					// build dispatcher
					List<Server> servers = sd.findAll();
					if(servers != null && servers.size() > 0) {
						for(Server server : servers) {
							saveDispatcher(taskId, server.getId());
						}
					}
					clear(destFile, userId, applicationId);
					return Status.Common.SUCCESS.getInt();
				}
			}
		}
		return Status.Common.ERROR.getInt();
	}
	
	private int saveTask(Integer userId, Integer applicationId, Integer users, Integer conversion,
			Integer dayRetention, Integer weekRetention, Integer monthRetention, 
			String district, String startDate, String endDate,
			Integer total) throws ParseException {
		Task task = new Task(userId, applicationId, users, conversion,
				dayRetention, weekRetention, monthRetention, district, 
				Molab.parseDate(startDate), Molab.parseDate(endDate), total, 0, 0, 0,
				System.currentTimeMillis(), Status.RunningStatus.START.getInt());
		int id = (Integer) dao.save(task);
		log.info("Task with id " + id + " saved success.");
		return id;
	}
	
	private int saveSubtask(Subtask st) {
		int id = (Integer) std.save(st);
		log.info("Subtask with id " + id + " saved success.");
		return id;
	}
	
	private int saveDispatcher(Integer taskId, Integer serverId) {
		Dispatcher dispatcher = new Dispatcher(taskId, serverId, Status.RunningStatus.START.getInt());
		int id = (Integer) dd.save(dispatcher);
		log.info("Dispatcher with id " + id + " saved success.");
		return id;
	}
	
	private void clear(File file, int userId, int applicationId) throws IOException {
		// move file
		String path = file.getParentFile().getAbsolutePath() + File.separator + userId;
		File userDir = new File(path);
		if(!userDir.exists()) {
			userDir.mkdirs();
		}
		File destFile = new File(userDir + File.separator + applicationId + ".apk");
		FileCopyUtils.copy(file, destFile);
		// delete ori file
//		file.delete();
		// delete xml file
//		new File(file.getAbsolutePath() + ".xml").delete();
//		new File(file.getAbsolutePath() + ".xml.xml").delete();
	}
	
	public int parseAction(List<MultipartFile> files, String path, int taskId, int actionType) throws IOException {
		for(MultipartFile mf : files) {
			if(!mf.isEmpty()) {
				CommonsMultipartFile cmf = (CommonsMultipartFile) mf;
				String action = read(cmf, path);
				saveAction(taskId, actionType, action);
			}
		}
		return Status.Common.SUCCESS.getInt();
	}
	
	private String read(CommonsMultipartFile file, String path) throws IOException {
		// create file
		String fileNameFull = path + File.separator + file.getOriginalFilename();
		File destFile = new File(fileNameFull);
		FileCopyUtils.copy(file.getBytes(), destFile);
		// read file
		BufferedReader reader = null;
		String action = "";
		try {
			reader = new BufferedReader(new FileReader(destFile));
			String buffer = null;
			while ((buffer = reader.readLine()) != null) {
				action += buffer;
			}
			reader.close();
		} catch (IOException e) {
			log.severe(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.severe(e.getMessage());
				}
			}
			destFile.deleteOnExit();
		}
		return action;
	}
	
	private int saveAction(Integer taskId, Integer actionType, String content) {
		Action action = new Action(taskId, actionType, content, 
				System.currentTimeMillis(), Status.Common.TRUE.getInt());
		int id = (Integer) acd.save(action);
		log.info("Action with id " + id + " saved success.");
		return id;
	}
	
}
