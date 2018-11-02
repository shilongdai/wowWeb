package net.viperfish.auth.core;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserRoles")
public class UserRole implements Serializable {

	private static final long serialVersionUID = -8582263309104817221L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Basic
	@Column(name = "username")
	private String username;
	@Basic
	@Column(name = "Rolename")
	private String rolename;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserId", nullable = false, updatable = false)
	private User user;

	public UserRole() {
		rolename = "user";
	}

	public UserRole(String username, String roleName) {
		this.rolename = roleName;
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserRole userRole = (UserRole) o;
		return id == userRole.id &&
			Objects.equals(username, userRole.username) &&
			Objects.equals(rolename, userRole.rolename);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, rolename);
	}
}
