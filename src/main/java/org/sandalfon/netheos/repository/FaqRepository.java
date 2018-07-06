package org.sandalfon.netheos.repository;

import java.util.List;

import org.sandalfon.netheos.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *  FAQ Repository 
 *  As the request/ project is simple there is no need for Repository Implementation 
 * Only the findByField is used
 * 
 * @author Gilles VIEIRA
 *
 */

@RepositoryRestResource(path = "/netheos/faq")
public interface FaqRepository extends JpaRepository<Faq, Long> {
	 Faq findById(Long questionId);
	 List<Faq> findByTags(String tags);
	
}