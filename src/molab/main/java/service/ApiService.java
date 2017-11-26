package molab.main.java.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import molab.main.java.component.api.Deviceinfo;
import molab.main.java.component.api.Proxyinfo;
import molab.main.java.component.api.TaskJson;
import molab.main.java.component.api.VcodeJson;
import molab.main.java.dao.ActionDao;
import molab.main.java.dao.AgentDao;
import molab.main.java.dao.AgentrunnerDao;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.ManufactureDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.dao.ProxyDao;
import molab.main.java.dao.SubtaskDao;
import molab.main.java.dao.TaskDao;
import molab.main.java.entity.Action;
import molab.main.java.entity.Agent;
import molab.main.java.entity.Agentrunner;
import molab.main.java.entity.Application;
import molab.main.java.entity.Manufacture;
import molab.main.java.entity.Model;
import molab.main.java.entity.Proxy;
import molab.main.java.entity.Subtask;
import molab.main.java.entity.Task;
import molab.main.java.util.Molab;
import molab.main.java.util.ProxyUtil;
import molab.main.java.util.Status;
import molab.main.java.util.sms.SmsUtil;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

	private static final Logger log = Logger.getLogger(ApiService.class.getName());
	
	@Autowired
	private ActionDao acd;
	
	@Autowired
	private AgentDao agd;
	
	@Autowired
	private AgentrunnerDao ard;
	
	@Autowired
	private ApplicationDao apd;
	
	@Autowired
	private ManufactureDao mad;

	@Autowired
	private ModelDao mod;
	
	@Autowired
	private ProxyDao pd;
	
	@Autowired
	private SubtaskDao std;
	
	@Autowired
	private TaskDao td;

	public int register() {
		Agent agent = new Agent(0, System.currentTimeMillis(), System.currentTimeMillis());
		int id = (Integer) agd.save(agent);
		log.info("Agent with id " + id + " saved success.");
		return id;
	}
	
	public TaskJson task(int agentId, String[] packagenames) throws ParseException {
		Agentrunner ar = findUndoneAr(agentId);
		if(ar != null && ar.getState() == Status.RunningStatus.START.getInt()) {
			// old task
			Subtask st = std.findById(ar.getSubtaskId());
			Task task = td.findById(st.getTaskId());
			return undoneStrategy(task, ar);
		} else {
			List<Subtask> subtaskList = findStListBySql(packagenames);
			if(subtaskList != null && subtaskList.size() > 0) {
				for(Subtask st : subtaskList) {
					// the subtask found
					if(st.getRunning() < st.getTotal()) {
						// fill data
						TaskJson json = buildTaskJson(st, agentId);
						if(json != null) {
							st.setRunning(st.getRunning() + 1);
							std.update(st);
							int taskid = saveAgentrunner(st.getId(), agentId, json);
							json.setTaskid(taskid);
							return json;
						}
					}
				}
			}
		}
		return null;
	}
	
	private List<Subtask> findStListBySql(String[] packagenames) {
		String date = String.valueOf(System.currentTimeMillis());
		String sql = 
				"SELECT * FROM SUBTASK S WHERE S.TASK_ID IN (" +
					"SELECT T.ID FROM TASK T WHERE T.APPLICATION_ID IN (" +
						"SELECT APP.ID FROM APPLICATION APP WHERE APP.PACKAGENAME IN (" +
							"''";
		for(String packagename : packagenames) {
			sql += ",'" + packagename + "'";
		}
				sql += ")" +
					") AND T.ID IN (" + 
						"SELECT A1.TASK_ID FROM ACTION A1, ACTION A2 " +
						"WHERE A1.STATE=1 AND A1.TYPE=1 AND A2.STATE=1 AND A2.TYPE=2 AND A1.TASK_ID=A2.TASK_ID" +
					") AND T.STATE=0" +
				") AND S.STATE=0 AND S.START_DATE<=" + date + " AND S.END_DATE>=" + date;
				
		List<Object> list = std.findBySql(sql);
		if(list != null && list.size() > 0) {
			List<Subtask> stList = new ArrayList<Subtask>();
			for(Object object : list) {
				stList.add(parseToSubtask(object));
			}
			return stList;
		}
		return null;
	}
	
	private Subtask parseToSubtask(Object object) {
		Object[] obj = (Object[]) object;
		return new Subtask((Integer) obj[0], (Integer) obj[1],
				(Integer) obj[2], (Integer) obj[3], (Integer) obj[4],
				(Integer) obj[5], (Integer) obj[6], (Integer) obj[7],
				(Integer) obj[8], (Integer) obj[9], (Integer) obj[10],
				(Integer) obj[11], Long.parseLong(obj[12].toString()),
				Long.parseLong(obj[13].toString()), (Integer) obj[14],
				(Integer) obj[15], (Integer) obj[16], (Integer) obj[17],
				(Integer) obj[18], (Integer) obj[19]);
	}
	
	private TaskJson buildTaskJson(Subtask st, Integer agentId) throws ParseException {
		Task task = td.findById(st.getTaskId());
		Agentrunner ar = null;
		if(st.getDrUsers() > 0 && st.getDrDone() < st.getDrUsers()) {
			// day retention user
			Integer[] isretent = {0};
			ar = findRetentionAr(st, -1, isretent);
			if(ar != null) { // found
				st.setDrDone(st.getDrDone() + 1);
				ar.setIsretent(1);
			}
		} else {
			if(st.getWrUsers() > 0 && st.getWrDone() < st.getWrUsers()) {
				// week retention user
				Integer[] isretent = {0, 1};
				ar = findRetentionAr(st, -7, isretent);
				if(ar != null) { // found
					st.setWrDone(st.getWrDone() + 1);
					ar.setIsretent(7);
				}
			} else {
				if(st.getMrUsers() > 0 && st.getMrDone() < st.getMrUsers()) {
					// month retention user
					Integer[] isretent = {0, 1, 7};
					ar = findRetentionAr(st, -30, isretent);
					if(ar != null) { // found
						st.setMrDone(st.getMrDone() + 1);
						ar.setIsretent(30);
					}
				}
			}
		}
		if(ar != null) { // retention found
			ard.update(ar);
			log.info("Agentrunner with id " + ar.getId() + " retent and isretent is " + ar.getIsretent());
			return retentionStrategy(task, ar);
		}
		if(st.getUsers() > 0) { // random strategy
			if(st.getSilentUsers() > 0 && st.getUsersDone() < st.getSilentUsers()) { // silent user
				st.setUsersDone(st.getUsersDone() + 1);
				TaskJson json = randomStrategy(task, agentId, Status.ActionType.SILENT.getInt());
				if(json != null) {
					json.setRetentionState(Status.RetentionDays.SILENT.getInt());
				}
				return json;
			}
			if(st.getUsersDone() < st.getUsers()) { // active user
				st.setUsersDone(st.getUsersDone() + 1);
				return randomStrategy(task, agentId, Status.ActionType.ACTIVE.getInt());
			}
		}
		return null;
	}
	
	/**
	 * find T-diffDays' retention agentrunner
	 * @param diffDays 
	 * */
	private Agentrunner findRetentionAr(Subtask st, Integer diffDays, Integer[] isretent) throws ParseException {
		long startDate = Molab.parseDate(st.getStartDate(), diffDays);
		long endDate = Molab.parseDate(st.getStartDate(), diffDays + 1) - 1;
		// find T-diffDays's subtask list
		DetachedCriteria dc = DetachedCriteria.forClass(Subtask.class);
		dc.add(Restrictions.eq("taskId", st.getTaskId()))
			.add(Restrictions.eq("startDate", startDate))
			.add(Restrictions.eq("endDate", endDate));
		List<Subtask> stList = std.findByCriteria(dc, 0, 1);
		if(stList != null && stList.size() > 0) {
			dc = DetachedCriteria.forClass(Agentrunner.class);
			dc.add(Restrictions.eq("subtaskId", stList.get(0).getId()))
				.add(Restrictions.in("isretent", isretent))
				.add(Restrictions.between("date", startDate, endDate));
			List<Agentrunner> arList = ard.findByCriteria(dc, 0, 1);
			if(arList != null && arList.size() > 0) {
				return arList.get(0);
			}
		}
		return null;
	}
	
	private TaskJson randomStrategy(Task task, Integer agentId, Integer actionType) {
		TaskJson json = new TaskJson();
		// get basic info
		Application application = apd.findById(task.getApplicationId());
		json.setProcname(application.getPackagename());
		json.setStartpage(application.getStartactivity());
		// random device info
		Deviceinfo deviceinfo = new Deviceinfo();
		deviceinfo.setImei(Molab.randomImei());
		deviceinfo.setMac(Molab.randomMac());
		deviceinfo.setAndroidid(Molab.randomAndroidid());
		// -- random meta
		List<Model> modelList = mod.findAll();
		if(modelList != null && modelList.size() > 0) {
			int index = (int) (Math.random() * (modelList.size() - 1));
			Model model = modelList.get(index);
			Manufacture manufacture = mad.findById(model.getManufactureId());
			deviceinfo.setManufacture(manufacture.getName());
			deviceinfo.setModel(model.getName());
			deviceinfo.setOs(model.getOs());
			String[] network = model.getNetwork().split(",");
			int i = new java.util.Random().nextInt(network.length);
			deviceinfo.setNetwork(network[i]);
		}
		json.setDeviceinfo(deviceinfo);
		// random proxy info
		Proxy proxy = findUsingProxy(agentId, task.getDistrict());
		if(proxy != null) {
			Proxyinfo proxyinfo = new Proxyinfo();
			proxyinfo.setId(proxy.getId());
			proxyinfo.setIp(proxy.getIp());
			proxyinfo.setPort(proxy.getPort());
			json.setProxyinfo(proxyinfo);
		}
		// get mobile
		Integer pid = application.getPid();
		if(pid > 0) {
			json.setMobile(SmsUtil.getMobilenum(pid));
		}
		// get action
		json = findAction(json, task.getId(), actionType);
		return json;
	}
	
	private TaskJson retentionStrategy(Task task, Agentrunner ar) {
		TaskJson json = baseStrategy(task, ar);
		// get action2
		json = findAction(json, task.getId(), Status.ActionType.RETENTION.getInt());
		return json;
	}
	
	private TaskJson undoneStrategy(Task task, Agentrunner ar) {
		TaskJson json = baseStrategy(task, ar);
		// get action
		json.setAction(acd.findById(ar.getActionId()).getContent());
		return json;
	}
	
	private TaskJson baseStrategy(Task task, Agentrunner ar) {
		TaskJson json = new TaskJson();
		// get basic info
		Application application = apd.findById(task.getApplicationId());
		json.setProcname(application.getPackagename());
		json.setStartpage(application.getStartactivity());
		json.setTaskid(ar.getId());
		// retention device info
		Deviceinfo deviceinfo = new Deviceinfo();
		deviceinfo.setImei(ar.getImei());
		deviceinfo.setMac(ar.getMac());
		deviceinfo.setAndroidid(ar.getAndroidid());
		deviceinfo.setManufacture(ar.getManufacture());
		deviceinfo.setModel(ar.getModel());
		deviceinfo.setOs(ar.getOs());
		deviceinfo.setNetwork(ar.getNetwork());
		json.setDeviceinfo(deviceinfo);
		// retention proxy info
		Proxy proxy = findAvailableProxy(task.getDistrict());
		if(proxy != null) {
			Proxyinfo proxyinfo = new Proxyinfo();
			proxyinfo.setId(proxy.getId());
			proxyinfo.setIp(proxy.getIp());
			proxyinfo.setPort(proxy.getPort());
			json.setProxyinfo(proxyinfo);
		}
		// retention mobile
		json.setMobile(ar.getMobile());
		return json;
	}
	
	private Integer saveAgentrunner(int stId, int agentId, TaskJson json) throws ParseException {
		Agentrunner ar = new Agentrunner(stId, agentId, System.currentTimeMillis(),
				Status.Common.FALSE.getInt(), Status.RunningStatus.START.getInt());
		if(json.getProxyinfo() != null) {
			ar.setProxyId(json.getProxyinfo().getId());
		}
		ar.setActionId(json.getActionId());
		ar.setImei(json.getDeviceinfo().getImei());
		ar.setMac(json.getDeviceinfo().getMac());
		ar.setAndroidid(json.getDeviceinfo().getAndroidid());
		ar.setManufacture(json.getDeviceinfo().getManufacture());
		ar.setModel(json.getDeviceinfo().getModel());
		ar.setOs(json.getDeviceinfo().getOs());
		ar.setNetwork(json.getDeviceinfo().getNetwork());
		ar.setMobile(json.getMobile());
		ar.setIsretent(json.getRetentionState());
		Integer id = (Integer) ard.save(ar);
		if(json.getRetentionState() == Status.RetentionDays.SILENT.getInt()) {
			log.info("Agentrunner@SILENT with id " + id + " saved success.");
		} else {
			log.info("Agentrunner with id " + id + " saved success.");
		}
		return id;
	}
	
	private TaskJson findAction(TaskJson json, Integer taskId, Integer type) {
		Action action = findAction(taskId, type);
		if(action != null) {
			json.setActionId(action.getId());
			json.setAction(action.getContent());
			return json;
		}
		return null;
	}
	
	private Action findAction(Integer taskId, Integer type) {
		DetachedCriteria dc = DetachedCriteria.forClass(Action.class);
		dc.add(Restrictions.eq("taskId", taskId))
			.add(Restrictions.eq("type", type))
			.add(Restrictions.eq("state", Status.Common.TRUE.getInt()));
		List<Action> actionList = acd.findByCriteria(dc);
		if(actionList != null && actionList.size() > 0) {
			int index = new Random().nextInt(actionList.size());
			return actionList.get(index);
		}
		return null;
	}
	
	private Proxy findAvailableProxy(String district) {
		DetachedCriteria dc = DetachedCriteria.forClass(Proxy.class);
		dc.add(Restrictions.eq("isused", Status.Common.FALSE.getInt()))
			.addOrder(Order.desc("time"));
		if(!district.startsWith("0")) { // district limited
			String[] districts = district.split(",");
			Integer[] districtCodes = new Integer[districts.length];
			for(int i = 0; i < districts.length; i++) {
				districtCodes[i] = Integer.parseInt(districts[i]);
			}
			dc.add(Restrictions.in("districtCode", districtCodes));
		}
		List<Proxy> proxyList = pd.findByCriteria(dc, 0, 1);
		if(proxyList != null && proxyList.size() > 0) {
			return proxyList.get(0);
		} else {
			return null;
		}
	}
	
	private Proxy findUsingProxy(Integer agentId, String district) {
		Proxy proxy = ProxyUtil.get(agentId);
		if(proxy == null) {
			proxy = findAvailableProxy(district);
		}
		if(proxy != null) {
			checkProxy(agentId, proxy, district);
		}
		return proxy;
	}
	
	private void checkProxy(final Integer agentId, 
			final Proxy proxy, final String district) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if(!ProxyUtil.check(proxy)) {
					ProxyUtil.update(agentId, findAvailableProxy(district));
					// update database
					proxy.setIsused(Status.RunningStatus.END.getInt());
					pd.update(proxy);
				} else {
					ProxyUtil.update(agentId, proxy);
				}
			}
			
		}).start();
	}
	
	public int response(Integer arId, Integer agentId, Integer error, Integer message) {
		Agentrunner ar = ard.findById(arId);
		if(ar != null) {
			Subtask st = std.findById(ar.getSubtaskId());
			if(st != null && st.getRunning() > 0) {
				// update subtask
				st.setRunning(st.getRunning() - 1);
				if(error == 0) { // OK
					st.setPass(st.getPass() + 1);
				} else {
					st.setError(st.getError() + 1);
				}
				int actual = st.getActual() + 1; // actual++
				st.setActual(actual);
				if(actual == st.getTotal()) {
					st.setState(Status.RunningStatus.END.getInt());
				}
				std.update(st);
				// update task
				Task task = td.findById(st.getTaskId());
				if(task != null) {
					if(error == 0) { // OK
						task.setPass(task.getPass() + 1);
					} else {
						task.setError(task.getError() + 1);
					}
					actual = task.getActual() + 1; // actual++
					task.setActual(actual);
					if(actual == task.getTotal()) {
						task.setState(Status.RunningStatus.END.getInt());
					}
					td.update(task);
				}
				// update ar
				ar.setState(Status.RunningStatus.END.getInt());
				ard.update(ar);
				return Status.Common.SUCCESS.getInt();
			}
		}
		return Status.Common.ERROR.getInt();
	}
	
	private long overtime = 3600000L; // 1 hours
	private Agentrunner findUndoneAr(int agentId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Agentrunner.class);
		dc.add(Restrictions.eq("agentId", agentId))
			.add(Restrictions.gt("date", System.currentTimeMillis() - overtime)) // only in <overtime> milliseconds
			.addOrder(Order.desc("id"));
		List<Agentrunner> arList = ard.findByCriteria(dc, 0, 1);
		if(arList != null && arList.size() > 0) {
			return arList.get(0);
		}
		return null;
	}
	
	public VcodeJson vcode(Integer taskid, String mobile) {
		try {
			// get vcode from sms platform
			Agentrunner ar = ard.findById(taskid);
			Subtask st = std.findById(ar.getSubtaskId());
			Task task = td.findById(st.getTaskId());
			Application application = apd.findById(task.getApplicationId());
			String vcode = SmsUtil.getVcode(application.getPid(), mobile);
			// vcode json
			VcodeJson json = new VcodeJson();
			json.setTaskid(taskid);
			json.setMobile(mobile);
			json.setVcode(vcode);
			return json;
		} catch(NullPointerException e) {
			log.severe(e.getMessage());
		}
		return null;
	}
	
}
