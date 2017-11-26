package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Installation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "INSTALLATION", catalog = "AST")
public class Installation implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer dispatcherId;
	private String sn;
	private String result;
	private Integer state;
	private Long time;

	// Constructors

	/** default constructor */
	public Installation() {
	}

	/** minimal constructor */
	public Installation(Integer dispatcherId, String sn, Integer state,
			Long time) {
		this.dispatcherId = dispatcherId;
		this.sn = sn;
		this.state = state;
		this.time = time;
	}

	/** full constructor */
	public Installation(Integer dispatcherId, String sn, String result,
			Integer state, Long time) {
		this.dispatcherId = dispatcherId;
		this.sn = sn;
		this.result = result;
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

	@Column(name = "DISPATCHER_ID", nullable = false)
	public Integer getDispatcherId() {
		return this.dispatcherId;
	}

	public void setDispatcherId(Integer dispatcherId) {
		this.dispatcherId = dispatcherId;
	}
	
	@Column(name = "SN")
	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "RESULT")
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
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