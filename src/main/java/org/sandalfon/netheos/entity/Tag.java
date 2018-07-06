package org.sandalfon.netheos.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 *  Tag entity
 * 
 * @author Gilles VIEIRA
 *
 */

@Entity
public class Tag {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String content;
	
	 @ManyToMany(mappedBy = "tags")
	private List<Faq> faqs = new ArrayList<>();
	
	 public Tag() {
	 }
	 
	 public Tag(String content) {
		 this.content = content;
	 }
	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	//  /!\ Avoid JSON infinite loop for many to many relation
	@JsonIgnore
	public List<Faq> getFaqs() {
		return faqs;
	}

	public void setFaqs(List<Faq> faqs) {
		this.faqs = faqs;
	}
	

	
}
