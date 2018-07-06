package org.sandalfon.netheos.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Restriction to the request for the Tag controller
 * 
 * @author Gilles VIEIRA
 *
 */

@Service
public interface TagService {

    @Secured ({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?>getByTags(@RequestBody List<String> tagsString, Authentication auth);
    @Secured ({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?>getByPhrase(@RequestBody String phrase, Authentication auth);

}
