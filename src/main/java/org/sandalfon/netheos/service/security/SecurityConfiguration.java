package org.sandalfon.netheos.service.security;

import org.sandalfon.netheos.service.entity.Account;
import org.sandalfon.netheos.service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountRepository accountRepository;

	@Autowired private PasswordEncoder passwordEncoder;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            Account user = accountRepository.findByUsername(username);
            if (user != null) {
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                        true, true, true, true, AuthorityUtils.createAuthorityList(user.getRole()));
            } else {
                throw new UsernameNotFoundException("Could not find the user '" + username + "'");
            }
        }).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic().and().csrf().disable();
    }
}