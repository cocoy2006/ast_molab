package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Agent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AGENT", catalog = "AST")
public class Agent implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer uses;
	private Long time;
	private Long lasttime;

	// Constructors

	/** default constructor */
	public Agent() {
	}

	/** minimal constructor */
	public Agent(Integer uses) {
		this.uses = uses;
	}

	/** full constructor */
	public Agent(Integer uses, Long time, Long lasttime) {
		this.uses = uses;
		this.time = time;
		this.lasttime = lasttime;
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

	@Column(name = "USES", nullable = false)
	public Integer getUses() {
		return this.uses;
	}

	public void setUses(Integer uses) {
		this.uses = uses;
	}

	@Column(name = "TIME")
	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Column(name = "LASTTIME")
	public Long getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Long lasttime) {
		this.lasttime = lasttime;
	}

}