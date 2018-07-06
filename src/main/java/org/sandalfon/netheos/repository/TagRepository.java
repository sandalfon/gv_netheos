package org.sandalfon.netheos.repository;

import org.sandalfon.netheos.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *  Tag Repository 
 *  As the request/ project is simple there is no need for Repository Implementation 
 * Only the findByField is used
 * 
 * @author Gilles VIEIRA
 *
 */

@RepositoryRestResource(path = "/netheos/tag")
public interface TagRepository extends JpaRepository<Tag, Long> {
	 Tag findById(Long tagId);

	Tag findByContent(String content);

}
