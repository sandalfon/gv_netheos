package org.sandalfon.netheos.service.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.sandalfon.netheos.service.entity.Account;
import org.sandalfon.netheos.service.entity.Faq;
import org.sandalfon.netheos.service.entity.NestheosAuthentication;
import org.sandalfon.netheos.service.entity.Tag;
import org.sandalfon.netheos.service.repository.AccountRepository;
import org.sandalfon.netheos.service.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {
	@Autowired
	TagRepository tagRepository;
	@Autowired 
	private AccountRepository accountRepository;
	
	


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

	private  ResponseEntity<?>parseTags(Authentication auth, List<String> tagsString) {
	NestheosAuthentication authN = new NestheosAuthentication(auth);
	if(!authN.isAccount())
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	
	Iterator<String> iter = tagsString.iterator();
	List<Tag> tags = new ArrayList<Tag>();
	while (iter.hasNext()) {
	   String tag = iter.next();
	    if (tagRepository.findByContent(tag)!= null) {
	        tags.add(tagRepository.findByContent(tag));
	    }
	    
	}
	if(tags.size() == 0) {
		return new ResponseEntity<>("{\"Result\":\"No result\"}", HttpStatus.OK);	
	}
	return new ResponseEntity<>(evaluateTags(tags), HttpStatus.OK);
}

	
	
	@RequestMapping(method = RequestMethod.POST,
			value = "/searchByTags", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?>getByTags(@RequestBody List<String> tagsString,
			Authentication auth) {

	return parseTags(auth, tagsString);
	}

	
	@RequestMapping(method = RequestMethod.POST,
			value = "/searchByPhrase", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?>getByPhrase(@RequestBody String phrase,
			Authentication auth) {
		List<String> tagsString =  Arrays.asList(phrase.split("\\W+"));
		return parseTags(auth, tagsString);
	}
	

	//Get Sorted list of Faq by the number of tags matching
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
	
	//get the list of FAq sorted from the hashmap <number of match, faq>
	private List<Faq>foramtListFaq( HashMap<Integer,List<Faq>> faqCount) {
		List<Faq> faqs = new ArrayList<Faq>();
		
		List<Integer> sortedKeys=new ArrayList<Integer>(faqCount.keySet());
		Collections.sort(sortedKeys,Collections.reverseOrder());
		for(int index : sortedKeys) {
			System.out.println("index "+index);
			faqs.addAll(faqCount.get(index));
		}

		return faqs;
	}
	
	//reverse the hashmap<faq, number of matching tag>
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