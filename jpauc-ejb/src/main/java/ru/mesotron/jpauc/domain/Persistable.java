package ru.mesotron.jpauc.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@SuppressWarnings("serial")
public class Persistable implements ru.mesotron.jpauc.shared.Persistable {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Версия.
     * Необходима для RequestFactory.
     */
    @NotNull
    @Version
    @Column(columnDefinition="int4 default 0")
    private Integer version = 0;

    /**
     * Возвращает идентификатор.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор.
     * Не должен использоваться.
     * @param id
     */
    protected void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает версию.
     * @return
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Устанавливает версию.
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}
