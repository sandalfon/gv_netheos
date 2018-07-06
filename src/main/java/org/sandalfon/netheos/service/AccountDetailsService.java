package org.sandalfon.netheos.service;

import java.util.Arrays;

import org.sandalfon.netheos.entity.Account;
import org.sandalfon.netheos.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * create new userDetail from user in DB
 * 
 * @author Gilles VIEIRA
 *
 */

@Service
public class AccountDetailsService implements UserDetailsService {
	@Autowired
    private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

			Account activeAccount = accountRepository.findByUsername(username);
			GrantedAuthority authority = new SimpleGrantedAuthority(activeAccount.getRole());
			UserDetails userDetails = (UserDetails)new User(activeAccount.getUsername(),
					activeAccount.getPassword(), Arrays.asList(authority));
			return userDetails;
	}

}
