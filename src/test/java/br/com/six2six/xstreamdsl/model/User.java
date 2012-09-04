package br.com.six2six.xstreamdsl.model;

import java.util.List;

public class User {

	private String login;
	private List<Role> roles;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public enum Role {
		MANAGER_GUI,
		MANAGER_SCRIPT,
		MANAGER_JMX

	}
}
