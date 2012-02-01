package ru.mesotron.jpauc.domain;

import java.lang.String;
import javax.persistence.*;
import ru.mesotron.jpauc.domain.Persistable;

/**
 * Entity implementation class for Entity: ReferenceEntity
 *
 */
@MappedSuperclass
public class ReferenceEntity extends Persistable implements ru.mesotron.jpauc.shared.ReferenceEntity {

	private String code;
	private String name;
	private static final long serialVersionUID = 1L;

	public ReferenceEntity() {
		super();
	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}
