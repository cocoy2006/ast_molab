package molab.main.java.component.api;

public class TaskJson {

	private int taskid; // agentrunner id
	private String procname;
	private String startpage;
	private transient int actionId;
	private String action;
	private Deviceinfo deviceinfo;
	private Proxyinfo proxyinfo;
	private String mobile;
	private transient int retentionState;

	/**
	 * @return the taskid
	 */
	public int getTaskid() {
		return taskid;
	}

	/**
	 * @param taskid
	 *            the taskid to set
	 */
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	/**
	 * @return the procname
	 */
	public String getProcname() {
		return procname;
	}

	/**
	 * @param procname
	 *            the procname to set
	 */
	public void setProcname(String procname) {
		this.procname = procname;
	}

	/**
	 * @return the startpage
	 */
	public String getStartpage() {
		return startpage;
	}

	/**
	 * @param startpage
	 *            the startpage to set
	 */
	public void setStartpage(String startpage) {
		this.startpage = startpage;
	}

	/**
	 * @return the actionId
	 */
	public int getActionId() {
		return actionId;
	}

	/**
	 * @param actionId
	 *            the actionId to set
	 */
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the deviceinfo
	 */
	public Deviceinfo getDeviceinfo() {
		return deviceinfo;
	}

	/**
	 * @param deviceinfo
	 *            the deviceinfo to set
	 */
	public void setDeviceinfo(Deviceinfo deviceinfo) {
		this.deviceinfo = deviceinfo;
	}

	/**
	 * @return the proxyinfo
	 */
	public Proxyinfo getProxyinfo() {
		return proxyinfo;
	}

	/**
	 * @param proxyinfo
	 *            the proxyinfo to set
	 */
	public void setProxyinfo(Proxyinfo proxyinfo) {
		this.proxyinfo = proxyinfo;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the retentionState
	 */
	public int getRetentionState() {
		return retentionState;
	}

	/**
	 * @param retentionState
	 *            the retentionState to set
	 */
	public void setRetentionState(int retentionState) {
		this.retentionState = retentionState;
	}

}
