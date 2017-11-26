package molab.main.java.component;

import java.util.Date;
import java.util.List;

import molab.main.java.entity.Action;
import molab.main.java.entity.Application;
import molab.main.java.entity.Task;
import molab.main.java.entity.User;

public class TaskComponent {

	private Task task;
	private Date startdate;
	private Date enddate;
	private Date time;
	private User user;
	private Application application;
	private List<SubtaskComponent> stcList;
	private List<Action> actionActiveList;
	private List<Action> actionSilentList;
	private List<Action> actionRetentionList;

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * @return the startdate
	 */
	public Date getStartdate() {
		return startdate;
	}

	/**
	 * @param startdate
	 *            the startdate to set
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	/**
	 * @return the enddate
	 */
	public Date getEnddate() {
		return enddate;
	}

	/**
	 * @param enddate
	 *            the enddate to set
	 */
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * @return the stcList
	 */
	public List<SubtaskComponent> getStcList() {
		return stcList;
	}

	/**
	 * @param stcList
	 *            the stcList to set
	 */
	public void setStcList(List<SubtaskComponent> stcList) {
		this.stcList = stcList;
	}

	/**
	 * @return the actionActiveList
	 */
	public List<Action> getActionActiveList() {
		return actionActiveList;
	}

	/**
	 * @param actionActiveList
	 *            the actionActiveList to set
	 */
	public void setActionActiveList(List<Action> actionActiveList) {
		this.actionActiveList = actionActiveList;
	}

	/**
	 * @return the actionSilentList
	 */
	public List<Action> getActionSilentList() {
		return actionSilentList;
	}

	/**
	 * @param actionSilentList
	 *            the actionSilentList to set
	 */
	public void setActionSilentList(List<Action> actionSilentList) {
		this.actionSilentList = actionSilentList;
	}

	/**
	 * @return the actionRetentionList
	 */
	public List<Action> getActionRetentionList() {
		return actionRetentionList;
	}

	/**
	 * @param actionRetentionList
	 *            the actionRetentionList to set
	 */
	public void setActionRetentionList(List<Action> actionRetentionList) {
		this.actionRetentionList = actionRetentionList;
	}

}
