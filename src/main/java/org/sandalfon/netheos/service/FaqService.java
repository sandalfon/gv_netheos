package org.sandalfon.netheos.service;

import org.sandalfon.netheos.entity.Faq;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Restriction to the request for the FAQ controller
 * 
 * @author Gilles VIEIRA
 *
 */
@Service
public interface FaqService {
	@Secured ({"ROLE_ADMIN"})
	ResponseEntity<?>getAllFaq(Authentication auth);
	@Secured ({"ROLE_ADMIN", "ROLE_USER"})
	ResponseEntity<?> gatFaq(@PathVariable Long questionId, Authentication auth);
	@Secured ({"ROLE_ADMIN"})
	ResponseEntity<?> create(@RequestBody Faq faqInput, Authentication auth);
}
