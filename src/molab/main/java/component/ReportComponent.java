package molab.main.java.component;

import java.util.Date;
import java.util.List;

import molab.main.java.entity.Agentrunner;
import molab.main.java.entity.Application;
import molab.main.java.entity.Task;

public class ReportComponent {

	private Task task;
	private Date time;
	private Application application;
	private List<Agentrunner> arList;

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
	 * @return the arList
	 */
	public List<Agentrunner> getArList() {
		return arList;
	}

	/**
	 * @param arList
	 *            the arList to set
	 */
	public void setArList(List<Agentrunner> arList) {
		this.arList = arList;
	}

}
