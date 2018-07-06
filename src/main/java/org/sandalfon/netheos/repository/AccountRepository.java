package org.sandalfon.netheos.repository;

/**
 *  Account Repository 
 *  As the request/ project is simple there is no need for Repository Implementation 
 * Only the findByField is used
 * 
 * @author Gilles VIEIRA
 *
 */

import org.sandalfon.netheos.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/netheos/account")
public interface AccountRepository extends JpaRepository<Account, Long>{

	Account findById(Long accountid);
	Account findByUsername(String username);

}
