package com.webmonitor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@SequenceGenerator(name = "seq_appuser", sequenceName = "seq_appuser", allocationSize = 1, initialValue = 1)
public class ApplicationUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@Column(unique = true, nullable = false)
	private String username;

	private String password;

	@Temporal(TemporalType.DATE)
	private Date createDate;

	private Boolean isActiveUser;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_role", uniqueConstraints = @UniqueConstraint (columnNames = {"user_id", "role_id"}, name = "unique_role_user"), 
				joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "application_user", foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)),
				inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "role", foreignKey = @ForeignKey(name = "role_fk",value = ConstraintMode.CONSTRAINT))
			)	
	private List<Role> roles = new ArrayList<Role>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getIsActiveUser() {
		return isActiveUser;
	}

	public void setIsActiveUser(Boolean isActiveUser) {
		this.isActiveUser = isActiveUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationUser other = (ApplicationUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Collection<Role> getAuthorities() {
		return this.roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
