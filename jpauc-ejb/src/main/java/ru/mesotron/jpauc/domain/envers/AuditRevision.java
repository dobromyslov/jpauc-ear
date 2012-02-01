package ru.mesotron.jpauc.domain.envers;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import ru.mesotron.jpauc.domain.auth.User;
import ru.mesotron.jpauc.shared.HasId;

/**
 * Entity implementation class for Entity: RevisionEntity
 *
 */
@Entity
@Table(name = "envers_revision_entity")
//RevisionEntity(RevisionListener.class)
public class AuditRevision implements Serializable, HasId {

	@Id
	@RevisionNumber
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@RevisionTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private User user;

	public AuditRevision() {
		super();
	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
	    return user;
	}
	public void setUser(User param) {
	    this.user = param;
	}
   
}
