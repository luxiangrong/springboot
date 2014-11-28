package com.kai.springboot.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "host", "port" }))
public class ShieldServer {
	private static int DEFAULT_SSH_PORT = 22;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String host;
	private int port;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<ShieldUser> shieldUsers;

	@ManyToMany
	private Set<Operator> operators;

	public void addShieldUser(ShieldUser shieldUser) {
		if (shieldUsers == null) {
			shieldUsers = new HashSet<ShieldUser>();
		}
		shieldUsers.add(shieldUser);
	}

	public ShieldServer() {
	}

	public ShieldServer(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public ShieldServer(String host) {
		this(host, DEFAULT_SSH_PORT);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Set<ShieldUser> getShieldUsers() {
		return shieldUsers;
	}

	public void setShieldUsers(Set<ShieldUser> shieldUsers) {
		this.shieldUsers = shieldUsers;
	}

	public Set<Operator> getOperators() {
		return operators;
	}

	public void setOperators(Set<Operator> operators) {
		this.operators = operators;
	}

}
