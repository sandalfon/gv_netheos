package org.sandalfon.netheos.service.entity;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class NestheosAuthentication implements Authentication{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Authentication auth;
	public NestheosAuthentication(Authentication auth){
		this.auth = auth;
	}

	@Override
	public String getName() {
		return auth.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return auth.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return auth.getCredentials();
	}

	@Override
	public Object getDetails() {
		return auth.getDetails();
	}

	@Override
	public Object getPrincipal() {
		return auth.getPrincipal();
	}

	@Override
	public boolean isAuthenticated() {	
		return auth.isAuthenticated();
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		auth.setAuthenticated(isAuthenticated);

	}


	public  boolean isAdmin() {

		if(auth == null)
			return false;
		for (GrantedAuthority role : auth.getAuthorities()) {
			if (role.getAuthority().equals("ADMIN"))
				return true;
		}
		return false;
	}



	public  boolean isUser() {
		if(auth == null)
			return false;
		for (GrantedAuthority role : auth.getAuthorities()) {
			if (role.getAuthority().equals("USER"))
				return true;
		}
		return false;
	}

	public boolean isAccount() {
		if(auth == null)
			return false;
		return true;
	}



}
