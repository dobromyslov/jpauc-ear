package ru.mesotron.jpauc.shared;

import java.io.Serializable;

public interface Persistable extends Serializable, HasId {
	Integer getVersion();
	void setVersion(Integer version);
}
