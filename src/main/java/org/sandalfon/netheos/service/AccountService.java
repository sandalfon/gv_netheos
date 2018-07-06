package org.sandalfon.netheos.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Restriction to the request for the Account controller
 * 
 * @author Gilles VIEIRA
 *
 */

@Service
public interface AccountService {
	 @Secured ({"ROLE_ADMIN"})
	 ResponseEntity<?>onPostAccount(String username, String password, String role);
	 @Secured ({"ROLE_ADMIN"})
	 ResponseEntity<?> getAllAccounts();
     @Secured ({"ROLE_ADMIN", "ROLE_USER"})
	 ResponseEntity<?>onGetAccount(Long accountId, Authentication auth);
	
}
