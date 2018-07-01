package org.sandalfon.netheos.service.repository;

import java.util.List;

import org.sandalfon.netheos.service.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/faq")
public interface FaqRepository extends JpaRepository<Faq, Long> {
	 Faq findById(Long questionId);
	 List<Faq> findByTags(String tags);
	
}