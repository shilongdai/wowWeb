package net.viperfish.auth.core;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "account")
@NamedQueries(value = {
	@NamedQuery(name = "findAll", query = "SELECT u from User u"),
	@NamedQuery(name = "findByUsername", query = "SELECT u from User u WHERE u.username = :name"),
	@NamedQuery(name = "deleteByUsername", query = "DELETE FROM User u WHERE u.username = :name")}
)
public class User implements Serializable {

	private static final long serialVersionUID = 1911106879501623551L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Basic
	@Column(name = "username")
	private String username;
	@Basic
	@Column(name = "sha_pass_hash")
	private String hashedPassword;
	@Basic
	@Column(name = "email")
	private String email;
	@Basic
	@Column(name = "joindate")
	private Date joinDate;
	@Basic
	@Column(name = "last_login")
	private Date lastLogin;
	@Basic
	@Column(name = "os")
	private String os;

	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "user", fetch = FetchType.EAGER)
	private List<UserRole> roles;

	public User() {
		this.username = "";
		this.hashedPassword = "";
		this.email = "";
		this.joinDate = new Date();
		this.os = "Win";
		this.roles = new LinkedList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public void registerRole(UserRole role) {
		role.setUser(this);
		this.roles.add(role);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(id, user.id) &&
			Objects.equals(username, user.username) &&
			Objects.equals(hashedPassword, user.hashedPassword) &&
			Objects.equals(email, user.email) &&
			Objects.equals(joinDate, user.joinDate) &&
			Objects.equals(lastLogin, user.lastLogin) &&
			Objects.equals(os, user.os);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, hashedPassword, email, joinDate, lastLogin, os);
	}
}
