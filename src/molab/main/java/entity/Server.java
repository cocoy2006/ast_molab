package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Server entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SERVER", catalog = "AST")
public class Server implements java.io.Serializable {

	// Fields

	private Integer id;
	private String url;

	// Constructors

	/** default constructor */
	public Server() {
	}

	/** full constructor */
	public Server(String url) {
		this.url = url;
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

	@Column(name = "URL", nullable = false, length = 50)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}