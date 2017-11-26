package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MODEL", catalog = "AST")
public class Model implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer manufactureId;
	private String name;
	private String os;
	private String network;
	private Float occupancy;
	private Integer state;

	// Constructors

	/** default constructor */
	public Model() {
	}

	/** minimal constructor */
	public Model(Integer manufactureId, String name) {
		this.manufactureId = manufactureId;
		this.name = name;
	}

	/** normal constructor */
	public Model(Integer manufactureId, String name, Float occupancy,
			Integer state) {
		this.manufactureId = manufactureId;
		this.name = name;
		this.occupancy = occupancy;
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

	@Column(name = "MANUFACTURE_ID", nullable = false)
	public Integer getManufactureId() {
		return this.manufactureId;
	}

	public void setManufactureId(Integer manufactureId) {
		this.manufactureId = manufactureId;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "OS", nullable = false, length = 10)
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "NETWORK", nullable = false, length = 50)
	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	@Column(name = "OCCUPANCY", precision = 2, scale = 0)
	public Float getOccupancy() {
		return this.occupancy;
	}

	public void setOccupancy(Float occupancy) {
		this.occupancy = occupancy;
	}

	@Column(name = "STATE")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}