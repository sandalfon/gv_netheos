package org.sandalfon.netheos.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.sandalfon.netheos.entity.Account;
import org.sandalfon.netheos.entity.Faq;
import org.sandalfon.netheos.entity.Tag;
import org.sandalfon.netheos.repository.AccountRepository;
import org.sandalfon.netheos.repository.TagRepository;
import org.sandalfon.netheos.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Tag controller
 Tag can't be added from post request, they are added with the FAQ
 * 
 * @author Gilles VIEIRA
 *
 */

@RestController
public class TagController implements TagService{
	
	@Autowired
	TagRepository tagRepository;
	@Autowired 
	private AccountRepository accountRepository;
	
	//Parse the list of words and filter to keep only matching tags
	//The main purpose is to remove unwanted words from user phrase
	//Available for Admin and USER
	private  ResponseEntity<?>parseTags(Authentication auth, List<String> tagsString) {
		Account accountAuth = accountRepository.findByUsername(auth.getName());
		if(accountAuth == null)
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	
	Iterator<String> iter = tagsString.iterator();
	List<Tag> tags = new ArrayList<Tag>();
	while (iter.hasNext()) {
	   String tag = iter.next();
	    if (tagRepository.findByContent(tag)!= null) {
	        tags.add(tagRepository.findByContent(tag));
	    }
	    
	}
	//check if atleast one tag match
	if(tags.size() == 0) {
		return new ResponseEntity<>("{\"Result\":\"No result\"}", HttpStatus.OK);	
	}
	return new ResponseEntity<>(evaluateTags(tags), HttpStatus.OK);
}

	
	//Get the Faqs from a json list of string : User story 3
	//Available for Admin and USER
	@RequestMapping(method = RequestMethod.POST,
			value = "/netheos/searchByTags", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?>getByTags(@RequestBody List<String> tagsString,
			Authentication auth) {

	return parseTags(auth, tagsString);
	}

	//Get the Faqs from a user phrase : User story 3
	//Available for Admin and USER
	@RequestMapping(method = RequestMethod.POST,
			value = "/netheos/searchByPhrase", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?>getByPhrase(@RequestBody String phrase,
			Authentication auth) {
		List<String> tagsString =  Arrays.asList(phrase.split("\\W+"));
		return parseTags(auth, tagsString);
	}
	

	//Get descending Sorted list of FAQ by the number of tags matching
	private List<Faq> evaluateTags(List<Tag> tags){
		HashMap<Faq,Integer> faqCount = new HashMap<Faq,Integer>();
		for(Tag tag : tags) {
			List<Faq> faqs = tag.getFaqs();
			for(Faq faq : faqs) {
				if(faqCount.containsKey(faq)) {
					faqCount.put(faq,faqCount.get(faq)+1);
				}else {
					faqCount.put(faq,1);
				}
			}
		}
		return foramtListFaq(inverseKeyValue(faqCount));	
	}
	
	//get the list of FAQ sorted from the hashmap <number of match, faq>
	private List<Faq>foramtListFaq( HashMap<Integer,List<Faq>> faqCount) {
		List<Faq> faqs = new ArrayList<Faq>();
		
		List<Integer> sortedKeys=new ArrayList<Integer>(faqCount.keySet());
		Collections.sort(sortedKeys,Collections.reverseOrder());
		for(int index : sortedKeys) {
			faqs.addAll(faqCount.get(index));
		}
		return faqs;
	}
	
	//reverse the hashmap<faq, number of matching tag> => hashmap< number of matching tag, faq> 
	private HashMap<Integer,List<Faq>>  inverseKeyValue(HashMap<Faq,Integer> faqCount) {
		HashMap<Integer,List<Faq>> countFaq = new HashMap<Integer,List<Faq>>();
		for(Faq faq : faqCount.keySet()) {
			if(countFaq.containsKey(faqCount.get(faq))) {
				countFaq.get(faqCount.get(faq)).add(faq);
			}else {
				List<Faq> lst = new ArrayList<Faq>();
				lst.add(faq);
				countFaq.put(faqCount.get(faq),lst);
			}
		}
		return countFaq;	
	}


}