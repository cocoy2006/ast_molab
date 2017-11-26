package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Subtask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SUBTASK", catalog = "AST")
public class Subtask implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer taskId;
	private int percent;
	private int users;
	private int silentUsers;
	private int usersDone;
	private int drUsers;
	private int drDone;
	private int wrUsers;
	private int wrDone;
	private int mrUsers;
	private int mrDone;
	private Long startDate;
	private Long endDate;
	private int total;
	private int actual;
	private int running;
	private int pass;
	private int error;
	private int state;

	// Constructors

	/** default constructor */
	public Subtask() {
	}
	
	/** full constructor */
	public Subtask(Integer id, Integer taskId, Integer percent, Integer users, 
			Integer silentUsers,Integer usersDone, Integer drUsers, Integer drDone,
			Integer wrUsers, Integer wrDone, Integer mrUsers, Integer mrDone,
			Long startDate, Long endDate, Integer total, Integer actual,
			Integer running, Integer pass, Integer error, Integer state) {
		this.id = id;
		this.taskId = taskId;
		this.percent = percent;
		this.users = users;
		this.silentUsers = silentUsers;
		this.usersDone = usersDone;
		this.drUsers = drUsers;
		this.drDone = drDone;
		this.wrUsers = wrUsers;
		this.wrDone = wrDone;
		this.mrUsers = mrUsers;
		this.mrDone = mrDone;
		this.startDate = startDate;
		this.endDate = endDate;
		this.total = total;
		this.actual = actual;
		this.running = running;
		this.pass = pass;
		this.error = error;
		this.state = state;
	}

	/** full constructor */
	public Subtask(Integer taskId, Integer percent, Integer users,
			Integer usersDone, Integer drUsers, Integer drDone,
			Integer wrUsers, Integer wrDone, Integer mrUsers, Integer mrDone,
			Long startDate, Long endDate, Integer total, Integer actual,
			Integer running, Integer pass, Integer error, Integer state) {
		this.taskId = taskId;
		this.percent = percent;
		this.users = users;
		this.usersDone = usersDone;
		this.drUsers = drUsers;
		this.drDone = drDone;
		this.wrUsers = wrUsers;
		this.wrDone = wrDone;
		this.mrUsers = mrUsers;
		this.mrDone = mrDone;
		this.startDate = startDate;
		this.endDate = endDate;
		this.total = total;
		this.actual = actual;
		this.running = running;
		this.pass = pass;
		this.error = error;
		this.state = state;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TASK_ID", nullable = false)
	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Column(name = "PERCENT", nullable = false)
	public Integer getPercent() {
		return this.percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	@Column(name = "USERS", nullable = false)
	public Integer getUsers() {
		return this.users;
	}

	public void setUsers(Integer users) {
		this.users = users;
	}

	@Column(name = "SILENT_USERS", nullable = false)
	public int getSilentUsers() {
		return silentUsers;
	}

	public void setSilentUsers(int silentUsers) {
		this.silentUsers = silentUsers;
	}

	@Column(name = "USERS_DONE", nullable = false)
	public Integer getUsersDone() {
		return this.usersDone;
	}

	public void setUsersDone(Integer usersDone) {
		this.usersDone = usersDone;
	}

	@Column(name = "DR_USERS", nullable = false)
	public Integer getDrUsers() {
		return this.drUsers;
	}

	public void setDrUsers(Integer drUsers) {
		this.drUsers = drUsers;
	}

	@Column(name = "DR_DONE", nullable = false)
	public Integer getDrDone() {
		return this.drDone;
	}

	public void setDrDone(Integer drDone) {
		this.drDone = drDone;
	}

	@Column(name = "WR_USERS", nullable = false)
	public Integer getWrUsers() {
		return this.wrUsers;
	}

	public void setWrUsers(Integer wrUsers) {
		this.wrUsers = wrUsers;
	}

	@Column(name = "WR_DONE", nullable = false)
	public Integer getWrDone() {
		return this.wrDone;
	}

	public void setWrDone(Integer wrDone) {
		this.wrDone = wrDone;
	}

	@Column(name = "MR_USERS", nullable = false)
	public Integer getMrUsers() {
		return this.mrUsers;
	}

	public void setMrUsers(Integer mrUsers) {
		this.mrUsers = mrUsers;
	}

	@Column(name = "MR_DONE", nullable = false)
	public Integer getMrDone() {
		return this.mrDone;
	}

	public void setMrDone(Integer mrDone) {
		this.mrDone = mrDone;
	}

	@Column(name = "START_DATE", nullable = false)
	public Long getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE", nullable = false)
	public Long getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	@Column(name = "TOTAL", nullable = false)
	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Column(name = "ACTUAL", nullable = false)
	public Integer getActual() {
		return this.actual;
	}

	public void setActual(Integer actual) {
		this.actual = actual;
	}

	@Column(name = "RUNNING", nullable = false)
	public Integer getRunning() {
		return this.running;
	}

	public void setRunning(Integer running) {
		this.running = running;
	}

	@Column(name = "PASS", nullable = false)
	public Integer getPass() {
		return this.pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	@Column(name = "ERROR", nullable = false)
	public Integer getError() {
		return this.error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	@Column(name = "STATE", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}