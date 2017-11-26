package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Dispatcher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DISPATCHER", catalog = "AST")
public class Dispatcher implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer taskId;
	private Integer serverId;
	private String result;
	private Integer code;
	private Integer state;
	private Long time;

	// Constructors

	/** default constructor */
	public Dispatcher() {
	}

	/** minimal constructor */
	public Dispatcher(Integer taskId, Integer serverId, Integer state) {
		this.taskId = taskId;
		this.serverId = serverId;
		this.state = state;
	}

	/** full constructor */
	public Dispatcher(Integer taskId, Integer serverId, String result,
			Integer code, Integer state, Long time) {
		this.taskId = taskId;
		this.serverId = serverId;
		this.result = result;
		this.code = code;
		this.state = state;
		this.time = time;
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

	@Column(name = "SERVER_ID", nullable = false)
	public Integer getServerId() {
		return this.serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	@Column(name = "RESULT")
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "CODE")
	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Column(name = "STATE", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "TIME")
	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}