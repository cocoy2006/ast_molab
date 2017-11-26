package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Task entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TASK", catalog = "AST")
public class Task implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer applicationId;
	private Integer users;
	private Integer conversion;
	private Integer dayRetention;
	private Integer weekRetention;
	private Integer monthRetention;
	private String district;
	private Long startDate;
	private Long endDate;
	private int total;
	private int actual;
	private int pass;
	private int error;
	private Long time;
	private Integer state;

	// Constructors

	/** default constructor */
	public Task() {
	}

	/** minimal constructor */
	public Task(Integer userId, Integer applicationId, Integer users,
			Integer dayRetention, Integer weekRetention,
			Integer monthRetention, Long startDate, Long endDate,
			Integer total, Integer actual, Integer pass, Integer error,
			Integer state) {
		this.userId = userId;
		this.applicationId = applicationId;
		this.users = users;
		this.dayRetention = dayRetention;
		this.weekRetention = weekRetention;
		this.monthRetention = monthRetention;
		this.startDate = startDate;
		this.endDate = endDate;
		this.total = total;
		this.actual = actual;
		this.pass = pass;
		this.error = error;
		this.state = state;
	}

	/** full constructor */
	public Task(Integer userId, Integer applicationId, Integer users, Integer conversion,
			Integer dayRetention, Integer weekRetention, Integer monthRetention, 
			String district, Long startDate, Long endDate,
			Integer total, Integer actual, Integer pass, Integer error,
			Long time, Integer state) {
		this.userId = userId;
		this.applicationId = applicationId;
		this.users = users;
		this.conversion = conversion;
		this.dayRetention = dayRetention;
		this.weekRetention = weekRetention;
		this.monthRetention = monthRetention;
		this.district = district;
		this.startDate = startDate;
		this.endDate = endDate;
		this.total = total;
		this.actual = actual;
		this.pass = pass;
		this.error = error;
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

	@Column(name = "USER_ID", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "APPLICATION_ID", nullable = false)
	public Integer getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	@Column(name = "USERS", nullable = false)
	public Integer getUsers() {
		return this.users;
	}

	public void setUsers(Integer users) {
		this.users = users;
	}

	@Column(name = "CONVERSION", nullable = false)
	public Integer getConversion() {
		return conversion;
	}

	public void setConversion(Integer conversion) {
		this.conversion = conversion;
	}

	@Column(name = "DAY_RETENTION", nullable = false)
	public Integer getDayRetention() {
		return this.dayRetention;
	}

	public void setDayRetention(Integer dayRetention) {
		this.dayRetention = dayRetention;
	}

	@Column(name = "WEEK_RETENTION", nullable = false)
	public Integer getWeekRetention() {
		return this.weekRetention;
	}

	public void setWeekRetention(Integer weekRetention) {
		this.weekRetention = weekRetention;
	}

	@Column(name = "MONTH_RETENTION", nullable = false)
	public Integer getMonthRetention() {
		return this.monthRetention;
	}

	public void setMonthRetention(Integer monthRetention) {
		this.monthRetention = monthRetention;
	}

	@Column(name = "DISTRICT", nullable = false, length = 200)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "START_DATE", nullable = false)
	public Long getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE", nullable = false)
	public Long getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	@Column(name = "TOTAL", nullable = false)
	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Column(name = "ACTUAL", nullable = false)
	public Integer getActual() {
		return this.actual;
	}

	public void setActual(Integer actual) {
		this.actual = actual;
	}

	@Column(name = "PASS", nullable = false)
	public Integer getPass() {
		return this.pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	@Column(name = "ERROR", nullable = false)
	public Integer getError() {
		return this.error;
	}

	public void setError(Integer error) {
		this.error = error;
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