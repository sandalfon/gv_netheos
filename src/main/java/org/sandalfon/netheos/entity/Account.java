package org.sandalfon.netheos.entity;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Account entity
 * 
 * @author Gilles VIEIRA
 *
 */
@Entity
public class Account {

	private long id;
	private String username;
	private String password;
	private String role;


	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public Account setId(long id) {
		this.id = id;
		return this;
	}

	public Account setUsername(String username) {
		this.username = username;
		return this;
	}

	public Account setPassword(String password) {
		this.password = password;
		return this;
	}

	public Account setRole(String role) {
		this.role = role;
		return this;
	}

	@Override
	public String toString() {
		final StringJoiner sj = new StringJoiner(", ", "{ ", " }");
		sj.add("id = "       + Objects.toString(getId()));
		sj.add("username = " + Objects.toString(getUsername()));
		sj.add("password = " + Objects.toString(getPassword()));
		sj.add("role = "     + Objects.toString(getRole()));
		return "Account " + sj.toString();
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) { return true; }
		if (!(that instanceof Account)) { return false; }
		final Account thatAccount = (Account)that;
		if (this.getId() != thatAccount.getId()) {return false; }
		if (!Objects.equals(this.getUsername(), thatAccount.getUsername())) {return false; }
		if (!Objects.equals(this.getPassword(), thatAccount.getPassword())) {return false; }
		if (!Objects.equals(this.getRole(), thatAccount.getRole())) {return false; }
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + Long.hashCode(getId());
		hash = 31 * hash + Objects.hashCode(getUsername());
		hash = 31 * hash + Objects.hashCode(getPassword());
		hash = 31 * hash + Objects.hashCode(getRole());
		return hash;
	}

	
}