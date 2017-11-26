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
@Table(name = "PROXY", catalog = "AST")
public class Proxy implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ip;
	private Integer port;
	private Integer districtCode;
	private String districtName;
	private Integer isused;
	private Long time;

	// Constructors

	/** default constructor */
	public Proxy() {
	}

	/** minimal constructor */
	public Proxy(String ip, Integer port, Integer isused, Long time) {
		this.ip = ip;
		this.port = port;
		this.isused = isused;
		this.time = time;
	}
	
	/** full constructor */
	public Proxy(String ip, Integer port, Integer districtCode,
			String districtName, Integer isused, Long time) {
		this.ip = ip;
		this.port = port;
		this.districtCode = districtCode;
		this.districtName = districtName;
		this.isused = isused;
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

	@Column(name = "IP", nullable = false, length = 50)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "PORT", nullable = false)
	public Integer getPort() {
		return this.port;
	}
	
	public void setPort(Integer port) {
		this.port = port;
	}
	
	@Column(name = "DISTRICT_CODE", nullable = false)
	public Integer getDistrictCode() {
		return this.districtCode;
	}

	public void setDistrictCode(Integer districtCode) {
		this.districtCode = districtCode;
	}

	@Column(name = "DISTRICT_NAME", length = 10)
	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	@Column(name = "ISUSED", nullable = false)
	public Integer getIsused() {
		return this.isused;
	}
	
	public void setIsused(Integer isused) {
		this.isused = isused;
	}
	
	@Column(name = "TIME", nullable = false)
	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}