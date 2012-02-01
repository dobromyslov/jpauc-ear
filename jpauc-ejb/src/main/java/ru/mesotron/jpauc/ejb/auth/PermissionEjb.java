package ru.mesotron.jpauc.ejb.auth;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ru.mesotron.jpauc.domain.auth.Permission;
import ru.mesotron.jpauc.ejb.PersistableEjb;

@Stateless
@LocalBean
public class PermissionEjb extends PersistableEjb<Permission> {

}
