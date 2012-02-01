package ru.mesotron.jpauc.domain.auth;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ru.mesotron.jpauc.domain.Persistable;
import ru.mesotron.jpauc.domain.auth.Permission;
import java.util.HashSet;
import java.util.Set;

import ru.mesotron.jpauc.domain.auth.User;

/**
 * Entity implementation class for Entity: Group
 *
 */
@Entity
@Table(name="auth_group")
@SuppressWarnings("serial")
public class Group extends Persistable {

	@NotNull
    @Size(max = 255)
    private String name;
	
	@ManyToMany
	@JoinTable(
			name = "auth_group_permission",
			joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
	)
	private Set<Permission> permissions = new HashSet<Permission>();

	@ManyToMany
	@JoinTable(
			name = "auth_group_user",
			joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
		    inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
	private Set<User> users = new HashSet<User>();

	public Group() {
		super();
	}
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
