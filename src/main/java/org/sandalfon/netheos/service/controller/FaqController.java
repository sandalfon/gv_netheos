package org.sandalfon.netheos.service.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.sandalfon.netheos.service.entity.Faq;
import org.sandalfon.netheos.service.entity.Tag;
import org.sandalfon.netheos.service.repository.FaqRepository;
import org.sandalfon.netheos.service.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaqController {

	@Autowired
	FaqRepository faqRepository;
	@Autowired
	TagRepository tagRepository;
	@RequestMapping(method = RequestMethod.GET,
			value = "/faq}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllFaq() {

	
		return new ResponseEntity<>(faqRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET,
			value = "/faq/{questionId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> gatFaq(@PathVariable Long questionId) {

	System.out.println(faqRepository.findById(questionId).toString());
		return new ResponseEntity<>(faqRepository.findById(questionId), HttpStatus.OK);
	}


	
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST,
	value ="/faq/put")
	HttpHeaders create(@RequestBody Faq faqInput) {
		Faq faq = new Faq();
		faq.setQuestion(faqInput.getQuestion());
		faq.setAnswer(faqInput.getAnswer());
		//faq.setTags(faqInput.getTags());
		List<Tag> tags = new ArrayList<Tag>();
		for(Tag tag : faqInput.getTags()) {
		Tag tagRef = tagRepository.findByContent(tag.getContent());
		System.out.println("******"+tag.getContent());
		if(tagRef == null) {
			tagRef = new Tag(tag.getContent());
			tagRepository.save(tagRef);
		}
		tags.add(tagRef);
	}
	faq.setTags(tags);
		this.faqRepository.save(faq);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders
				.setLocation(linkTo(FaqController.class).slash(faq.getId()).toUri());

		return httpHeaders;
	}
	
	
}
