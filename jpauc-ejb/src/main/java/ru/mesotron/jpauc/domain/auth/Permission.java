package ru.mesotron.jpauc.domain.auth;

import javax.persistence.*;
import ru.mesotron.jpauc.domain.ReferenceEntity;

/**
 * Entity implementation class for Entity: Permission
 *
 */
@Entity
@Table(name = "auth_permission")
@SuppressWarnings("serial")
public class Permission extends ReferenceEntity {

	public Permission() {
		super();
	}
   
}
