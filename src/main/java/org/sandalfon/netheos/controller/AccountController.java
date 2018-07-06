package org.sandalfon.netheos.controller;

import org.sandalfon.netheos.entity.Account;
import org.sandalfon.netheos.repository.AccountRepository;
import org.sandalfon.netheos.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Account controller
 * For spring security compatibility use the prefix ROLE_ for the user role
 *  ROLE_{userRole}
 *  
 *  @author Gilles VIEIRA
 *
 */

@RestController
public class AccountController implements AccountService{
	@Autowired 
	private AccountRepository accountRepository;
	
	//Password encrytpyion 
	@Autowired 
	private PasswordEncoder passwordEncoder;

	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Create new account by request parameters
	//Only available for Admin
	@Override
	@RequestMapping(method = RequestMethod.POST,
	value = "netheos/account", 
	produces = MediaType.APPLICATION_JSON_VALUE)
	public
	ResponseEntity<?> onPostAccount(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("role") String role) {

		final Account created = new Account();
		created.setUsername(username);
		created.setPassword(passwordEncoder.encode(password));
		created.setRole(role);
		accountRepository.save(created);

		return new ResponseEntity<>(accountRepository.findById(created.getId()), HttpStatus.OK);

	}

	//Get all Account
	//Only available for Admin
	@RequestMapping(method = RequestMethod.GET,
	value = "netheos/account", 
	produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?> getAllAccounts(){
		return new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK);	
	}

	//Get a specific account
	//Available for Admin or User if the account is the User account
	@RequestMapping(method = RequestMethod.GET,
			value = "netheos/account/{accountId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?>onGetAccount(@PathVariable Long accountId,
			Authentication auth) {
		Account accountAuth = accountRepository.findByUsername(auth.getName());
		if(accountAuth == null)
			return new ResponseEntity<>(null, HttpStatus.OK);
		Account account = accountRepository.findById(accountId);
		if(account == null)
			return new ResponseEntity<>(null, HttpStatus.OK);
		if ("ROLE_ADMIN".equals(accountAuth.getRole())) {
			return new ResponseEntity<>(account, HttpStatus.OK);

		}
		if(account.getId() == accountAuth.getId())
			return new ResponseEntity<>(account, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}





}
