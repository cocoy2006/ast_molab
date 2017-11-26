package molab.main.java.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import molab.main.java.component.SubtaskComponent;
import molab.main.java.component.TaskComponent;
import molab.main.java.dao.ActionDao;
import molab.main.java.dao.AgentrunnerDao;
import molab.main.java.dao.ApplicationDao;
import molab.main.java.dao.SubtaskDao;
import molab.main.java.dao.TaskDao;
import molab.main.java.dao.UserDao;
import molab.main.java.entity.Action;
import molab.main.java.entity.Application;
import molab.main.java.entity.Subtask;
import molab.main.java.entity.Task;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

	@Autowired
	private ActionDao acd;
	
	@Autowired
	private AgentrunnerDao ard;
	
	@Autowired
	private ApplicationDao apd;
	
	@Autowired
	private SubtaskDao std;
	
	@Autowired
	private TaskDao td;
	
	@Autowired
	private UserDao ud;

	public TaskComponent findByTaskId(int taskId) {
		Task task = td.findById(taskId);
		if(task != null) {
			TaskComponent tc = new TaskComponent();
			tc.setTask(task);
			tc.setStartdate(new Date(task.getStartDate()));
			tc.setEnddate(new Date(task.getEndDate()));
			tc.setTime(new Date(task.getTime()));
			// fill application
			Application application = apd.findById(task.getApplicationId());
			if(application != null) {
				tc.setApplication(application);
			}
			// fill subtask
			List<Subtask> stList = std.findByTaskId(taskId);
			if(stList != null && stList.size() > 0) {
				List<SubtaskComponent> stcList = new ArrayList<SubtaskComponent>();
				for(Subtask st : stList) {
					SubtaskComponent stc = new SubtaskComponent();
					stc.setSt(st);
					stc.setStartdate(new Date(st.getStartDate()));
					stc.setEnddate(new Date(st.getEndDate() + 1));
//					List<Agentrunner> arList = ard.findBySubtaskId(st.getId());
//					if(arList != null && arList.size() > 0) {
//						stc.setArList(arList);
//					}
					stcList.add(stc);
				}
				tc.setStcList(stcList);
				
			}
			// fill action (type=ACTIVE)
			Action action = new Action();
			action.setTaskId(taskId);
			action.setType(Status.ActionType.ACTIVE.getInt());
			tc.setActionActiveList(acd.findByExample(action));
			// fill action (type=SILENT)
			action.setType(Status.ActionType.SILENT.getInt());
			tc.setActionSilentList(acd.findByExample(action));
			// fill action (type=RETENTION)
			action.setType(Status.ActionType.RETENTION.getInt());
			tc.setActionRetentionList(acd.findByExample(action));
			return tc;
		}
		return null;
	}
}
