package com.kai.springboot.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Operator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String authorizedKeys;

	@ManyToMany
	private Set<ShieldServer> shieldServers;

	@ManyToMany
	private Set<ShieldUser> shieldUsers;
}
