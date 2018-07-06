package org.sandalfon.netheos.security;


import org.sandalfon.netheos.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security restriction, access granted to user or admin only
 * 
 * @author Gilles VIEIRA
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccountDetailsService accountDetailsService;	
	@Autowired
	private NetheosAuthenticationEntryPoint netheosAuthenticationEntryPoint;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
		    .authorizeRequests()
		    .antMatchers("/netheos/**").hasAnyRole("ADMIN","USER")
		    .and().httpBasic().realmName("Netheos")
		    .authenticationEntryPoint(netheosAuthenticationEntryPoint);
	} 
	
     @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                auth.userDetailsService(accountDetailsService).passwordEncoder(passwordEncoder);
	}
}