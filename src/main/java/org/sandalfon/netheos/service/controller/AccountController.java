package org.sandalfon.netheos.service.controller;

import org.sandalfon.netheos.service.entity.Account;
import org.sandalfon.netheos.service.entity.NestheosAuthentication;
import org.sandalfon.netheos.service.repository.AccountRepository;
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

@RestController
public class AccountController {
	@Autowired 
	private AccountRepository accountRepository;
	@Autowired 
	private PasswordEncoder passwordEncoder;


	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@RequestMapping(method = RequestMethod.POST,
			value = "/account", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> onPostAccount(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("role") String role,
			Authentication auth) {
		NestheosAuthentication authN = new NestheosAuthentication(auth);

		if(!authN.isAccount())
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		if (authN.isAdmin()) {
			final Account created = new Account();
			created.setUsername(username);
			created.setPassword(passwordEncoder.encode(password));
			created.setRole(role);
			accountRepository.save(created);
			return new ResponseEntity<>(created, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}


	}


	@RequestMapping(method = RequestMethod.GET,
			value = "/account", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?> getAllAccounts(Authentication auth) {
		NestheosAuthentication authN = new NestheosAuthentication(auth);
		if(!authN.isAccount())
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

		if (authN.isAdmin()) {
			return new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK);

		}
		return new ResponseEntity<>(accountRepository.findByUsername(authN.getName()), HttpStatus.OK);
	}



	@RequestMapping(method = RequestMethod.GET,
			value = "/account/{accountId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?>onGetAccount(@PathVariable Long accountId,
			Authentication auth) {


		NestheosAuthentication authN = new NestheosAuthentication(auth);
		if(!authN.isAccount())
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

		Account account  = accountRepository.findById(accountId);
		if(account ==null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		if (authN.isAdmin()) {
			return new ResponseEntity<>(account, HttpStatus.OK);

		}
		if(accountId == accountRepository.findByUsername(authN.getName()).getId())
			return new ResponseEntity<>(account, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}





}
