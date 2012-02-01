package ru.mesotron.jpauc.domain.auth;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.digest.DigestUtils;

import ru.mesotron.jpauc.domain.Persistable;
import ru.mesotron.jpauc.domain.auth.Group;
import java.util.HashSet;
import java.util.Set;
import ru.mesotron.jpauc.domain.auth.Permission;


/**
 * The persistent class for the auth_user database table.
 * 
 */
@Entity
@Table(name="auth_user")
@SuppressWarnings("serial")
public class User extends Persistable {

	@NotNull
	@Column(columnDefinition="bool default true")
	private Boolean active = true;

	@NotNull
	@Size(min = 5, max = 50)
    @Column(unique = true)
	private String email;

	@NotNull
	@Size(min = 1, max = 25)
	private String firstName;

	@Size(max = 25)
	private String secondName;

	@NotNull
	@Size(min = 1, max = 30)
	private String lastName;

	@NotNull
    @Size(min = 6, max = 32)
    @Column(name="password")
	private String safePassword;

	@Transient
	@Size(min = 6)
    private String password;

	@ManyToMany(mappedBy = "users")
	private Set<Group> groups = new HashSet<Group>();

	@ManyToMany
    @JoinTable(
            name="auth_user_permission",
            joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="permission_id", referencedColumnName="id")
    )
	private Set<Permission> permissions = new HashSet<Permission>();

    public User() {
    }

    /**
     * Возвращает полное имя с инициалами
     * @return
     */
    public String getFullName() {
        return getFullName(false);
    }

    /**
     * Возвращает полное имя с инициалами или без
     * @param useInitials использовать инициалы
     * @return
     */
    public String getFullName(boolean useInitials) {
        String result = lastName;
        if (firstName != "") {
            result += " " + (useInitials ? firstName.charAt(0) + "." : firstName);
        }
        if (secondName != "") {
            result += " " + (useInitials ? secondName.charAt(0) + "." : secondName);
        }
        return result;
    }

    /**
     * Возвращает групповые права пользователя.
     * @return
     */
    public Set<Permission> getGroupPermissions() {
        Set<Permission> result = new HashSet<Permission>();
        for (Group g : getGroups()) {
        	result.addAll(g.getPermissions());
        }
        return result;
    }

    /**
     * Возвращает полные права пользователя, полученные из групповых и личных.
     * @return
     */
    public Set<Permission> getAllPermissions() {
        Set<Permission> result = getGroupPermissions();
        result.addAll(getPermissions());
        return result;
    }

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		setSafePassword(DigestUtils.md5Hex(password));
        this.password = password;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSafePassword() {
		return safePassword;
	}

	public void setSafePassword(String safePassword) {
		this.safePassword = safePassword;
	}

	public Set<Group> getGroups() {
	    return groups;
	}

	public void setGroups(Set<Group> param) {
	    this.groups = param;
	}

	public Set<Permission> getPermissions() {
	    return permissions;
	}

	public void setPermissions(Set<Permission> param) {
	    this.permissions = param;
	}
}