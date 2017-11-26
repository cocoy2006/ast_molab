package molab.main.java.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Agentrunner entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AGENTRUNNER", catalog = "AST")
public class Agentrunner implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer subtaskId;
	private Integer agentId;
	private Integer proxyId;
	private Integer actionId;
	private Long date;
	private Integer isretent;
	private String imei;
	private String sn;
	private String mac;
	private String androidid;
	private String manufacture;
	private String model;
	private String os;
	private String network;
	private String mobile;
	private Integer state;

	// Constructors

	/** default constructor */
	public Agentrunner() {
	}

	/** minimal constructor */
	public Agentrunner(Integer subtaskId, Integer agentId, Long date,
			Integer isretent, Integer state) {
		this.subtaskId = subtaskId;
		this.agentId = agentId;
		this.date = date;
		this.isretent = isretent;
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

	@Column(name = "SUBTASK_ID", nullable = false)
	public Integer getSubtaskId() {
		return this.subtaskId;
	}

	public void setSubtaskId(Integer subtaskId) {
		this.subtaskId = subtaskId;
	}

	@Column(name = "AGENT_ID", nullable = false)
	public Integer getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	@Column(name = "PROXY_ID")
	public Integer getProxyId() {
		return proxyId;
	}

	public void setProxyId(Integer proxyId) {
		this.proxyId = proxyId;
	}

	@Column(name = "ACTION_ID")
	public Integer getActionId() {
        return this.actionId;
    }
    
    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

	@Column(name = "DATE", nullable = false)
	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	@Column(name = "ISRETENT", nullable = false)
	public Integer getIsretent() {
		return this.isretent;
	}

	public void setIsretent(Integer isretent) {
		this.isretent = isretent;
	}

	@Column(name = "IMEI", length = 20)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Column(name = "SN", length = 20)
	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "MAC", length = 20)
	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	@Column(name = "ANDROIDID", length = 20)
	public String getAndroidid() {
		return androidid;
	}

	public void setAndroidid(String androidid) {
		this.androidid = androidid;
	}

	@Column(name = "MANUFACTURE", length = 20)
	public String getManufacture() {
		return manufacture;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	@Column(name = "MODEL", length = 20)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "OS", length = 10)
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "NETWORK", length = 50)
	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	@Column(name = "MOBILE", length = 20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "STATE", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}