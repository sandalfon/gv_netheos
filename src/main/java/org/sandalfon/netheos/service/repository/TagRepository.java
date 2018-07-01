package org.sandalfon.netheos.service.repository;

import org.sandalfon.netheos.service.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
	 Tag findById(Long tagId);

	Tag findByContent(String content);

}
