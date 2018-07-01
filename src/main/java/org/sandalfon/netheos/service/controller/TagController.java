package org.sandalfon.netheos.service.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.sandalfon.netheos.service.entity.Tag;
import org.sandalfon.netheos.service.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {
	@Autowired
	TagRepository tagRepository;
	
	@RequestMapping(method = RequestMethod.GET,
			value = "/tag}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllTag() {

	
		return new ResponseEntity<>(tagRepository.findAll(), HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST,
			value ="/tag/put")
	HttpHeaders create(@RequestBody Tag tagInput) {
		Tag tag = new Tag();
		tag.setContent(tagInput.getContent());

		tagRepository.save(tag);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(linkTo(TagController.class).slash(tag.getId()).toUri());

		return httpHeaders;
	}
	
	
}
