package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Manufacturer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MANUFACTURE", catalog = "AST")
public class Manufacture implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer state;

	// Constructors

	/** default constructor */
	public Manufacture() {
	}

	/** minimal constructor */
	public Manufacture(String name) {
		this.name = name;
	}

	/** full constructor */
	public Manufacture(String name, Integer state) {
		this.name = name;
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

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "STATE")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}