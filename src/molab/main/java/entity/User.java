package molab.main.java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER", catalog = "AST")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String nickname;
	private String password;
	private String email;
	private String phone;
	private Integer assets;
	private Integer state;
	private Integer role;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String username, String password, String email, Integer assets,
			Integer state, Integer role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.assets = assets;
		this.state = state;
		this.role = role;
	}

	/** full constructor */
	public User(String username, String nickname, String password,
			String email, String phone, Integer assets, Integer state,
			Integer role) {
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.assets = assets;
		this.state = state;
		this.role = role;
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

	@Column(name = "USERNAME", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "NICKNAME", length = 20)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "PASSWORD", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "EMAIL", nullable = false, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ASSETS")
	public Integer getAssets() {
		return assets;
	}

	public void setAssets(Integer assets) {
		this.assets = assets;
	}

	@Column(name = "STATE")
	public Integer getState() {
		return this.state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "ROLE")
	public Integer getRole() {
		return this.role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

}