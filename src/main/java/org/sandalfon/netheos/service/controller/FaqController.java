package org.sandalfon.netheos.service.controller;



import java.util.ArrayList;
import java.util.List;

import org.sandalfon.netheos.service.entity.Faq;
import org.sandalfon.netheos.service.entity.NestheosAuthentication;
import org.sandalfon.netheos.service.entity.Tag;
import org.sandalfon.netheos.service.repository.FaqRepository;
import org.sandalfon.netheos.service.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class FaqController {

	@Autowired
	private FaqRepository faqRepository;
	@Autowired
	private TagRepository tagRepository;



	@RequestMapping(method = RequestMethod.GET,
			value = "/faq", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllFaq(Authentication auth) {
		NestheosAuthentication authN = new NestheosAuthentication(auth);
		if(!authN.isAccount())
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		if (authN.isAdmin()) {
			
			return new ResponseEntity<>(faqRepository.findAll(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.GET,
			value = "/faq/{questionId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> gatFaq(@PathVariable Long questionId, Authentication auth) throws JsonProcessingException {
		NestheosAuthentication authN = new NestheosAuthentication(auth);
		if(!authN.isAccount())
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		if (authN.isAdmin()) {
			Faq faq = faqRepository.findById(questionId);
			if(faq!=null) {
				return new ResponseEntity<>(faq, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}




	@RequestMapping(method = RequestMethod.POST,
			value = "/faq", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> create(@RequestBody Faq faqInput,	Authentication auth) {

		NestheosAuthentication authN = new NestheosAuthentication(auth);

		if(!authN.isAccount())
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		if (authN.isAdmin()) {
			Faq faq = new Faq();
			faq.setQuestion(faqInput.getQuestion());
			faq.setAnswer(faqInput.getAnswer());
			List<Tag> tags = new ArrayList<Tag>();
			for(Tag tag : faqInput.getTags()) {
				Tag tagRef = tagRepository.findByContent(tag.getContent());
				if(tagRef == null) {
					tagRef = new Tag(tag.getContent());
					tagRepository.save(tagRef);
				}
				tags.add(tagRef);
			}
			faq.setTags(tags);
			faqRepository.save(faq);
			return new ResponseEntity<>(faq, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}


}
