package br.com.six2six.xstreamwriterdsl.model;

import java.util.Date;

public class Client {

	private Long id;
	private String name;
	private String nickname;
	private String email;
	private Date birthday;
	private Address address;
	
	private Client() { }
	
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
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Client [address=" + address + ", birthday=" + birthday + ", email=" + email + ", id=" + id + ", name=" + name + ", nickname=" + nickname + "]";
	}

}
