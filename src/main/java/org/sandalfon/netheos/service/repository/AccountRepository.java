package org.sandalfon.netheos.service.repository;


import org.sandalfon.netheos.service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/account")
public interface AccountRepository extends JpaRepository<Account, Long>{

	Account findById(Long accountid);
	Account findByUsername(String username);

}
