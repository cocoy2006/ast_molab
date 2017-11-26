package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * District entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DISTRICT", catalog = "AST")
public class District implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer code;
	private String name;
	private String short_;

	// Constructors

	/** default constructor */
	public District() {
	}

	/** minimal constructor */
	public District(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	/** full constructor */
	public District(Integer code, String name, String short_) {
		this.code = code;
		this.name = name;
		this.short_ = short_;
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

	@Column(name = "CODE", nullable = false)
	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Column(name = "NAME", nullable = false, length = 10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SHORT", length = 10)
	public String getShort_() {
		return this.short_;
	}

	public void setShort_(String short_) {
		this.short_ = short_;
	}

}