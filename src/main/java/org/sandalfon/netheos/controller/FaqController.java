package org.sandalfon.netheos.controller;

import java.util.ArrayList;
import java.util.List;

import org.sandalfon.netheos.entity.Account;
import org.sandalfon.netheos.entity.Faq;
import org.sandalfon.netheos.entity.Tag;
import org.sandalfon.netheos.repository.AccountRepository;
import org.sandalfon.netheos.repository.FaqRepository;
import org.sandalfon.netheos.repository.TagRepository;
import org.sandalfon.netheos.service.FaqService;
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


/**
 * FAQ controller
 * @author Sandalfon
 *
 */
@RestController
public class FaqController implements FaqService{

	@Autowired
	private FaqRepository faqRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired 
	private AccountRepository accountRepository;


	//Get all the faq : User story 2
	//Only available for Admin
	@RequestMapping(method = RequestMethod.GET,
			value = "netheos/faq", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllFaq(Authentication auth) {
			return new ResponseEntity<>(faqRepository.findAll(), HttpStatus.OK);
	}

	//Get a specific faq 
	//Available for Admin and USER
	//Not in the requirement but, it for my personal test 
	@RequestMapping(method = RequestMethod.GET,
			value = "netheos/faq/{questionId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> gatFaq(@PathVariable Long questionId, Authentication auth) {
		Account accountAuth = accountRepository.findByUsername(auth.getName());
		if(accountAuth == null)
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		if ("ROLE_ADMIN".equals(accountAuth.getRole())) {
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

	//Create a faq from a JSON : User story 1
	//Only available for Admin
	
	@RequestMapping(method = RequestMethod.POST,
			value = "netheos/faq", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public 	ResponseEntity<?> create(@RequestBody Faq faqInput,	Authentication auth) {

		Account accountAuth = accountRepository.findByUsername(auth.getName());
		if(accountAuth == null)
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
				Faq faq = new Faq();
			faq.setQuestion(faqInput.getQuestion());
			faq.setAnswer(faqInput.getAnswer());
			List<Tag> tags = new ArrayList<Tag>();
			
			//Check if tag exist, if not create a new tag, else find the corresponding tag in the DB
			
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


}
