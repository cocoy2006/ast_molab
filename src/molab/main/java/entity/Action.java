package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Action entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ACTION", catalog = "AST")
public class Action implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer taskId;
	private Integer type;
	private String content;
	private Long time;
	private Integer state;

	// Constructors

	/** default constructor */
	public Action() {
	}

	/** minimal constructor */
	public Action(Integer taskId, Integer type, String content) {
		this.taskId = taskId;
		this.type = type;
		this.content = content;
	}

	/** full constructor */
	public Action(Integer taskId, Integer type, String content, Long time,
			Integer state) {
		this.taskId = taskId;
		this.type = type;
		this.content = content;
		this.time = time;
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

	@Column(name = "TYPE")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "TIME")
	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Column(name = "STATE", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}