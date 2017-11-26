package molab.main.java.component;

import java.util.Date;
import java.util.List;

import molab.main.java.entity.Agentrunner;
import molab.main.java.entity.Subtask;

public class SubtaskComponent {

	private Subtask st;
	private Date startdate;
	private Date enddate;
	private List<Agentrunner> arList;

	/**
	 * @return the st
	 */
	public Subtask getSt() {
		return st;
	}

	/**
	 * @param st
	 *            the st to set
	 */
	public void setSt(Subtask st) {
		this.st = st;
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
