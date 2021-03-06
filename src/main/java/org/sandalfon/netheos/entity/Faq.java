package org.sandalfon.netheos.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *  FAQ entity
 * 
 * @author Gilles VIEIRA
 *
 */

@Entity
public class Faq {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String question;
	private String answer;
	
	//many to many relation wuith tags 
	@ManyToMany(cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE
	})
	@JoinTable(name = "faq_tag",
	joinColumns = @JoinColumn(name = "faq_id"),
	inverseJoinColumns = @JoinColumn(name = "tag_id")
			)
	private List<Tag> tags = new ArrayList<>();

	public void addTag(Tag tag) {
		tags.add(tag);
		tag.getFaqs().add(this);
	}

	public void removeTag(Tag tag) {
		tags.remove(tag);
		tag.getFaqs().remove(this);
	}


	public Long getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		String tag = "";
		if(tags.size()>0) {
			tag = tags.get(0).getContent();

			for(int i = 1; i< tags.size();i++) {
				tag = tag +", "+tags.get(i).getContent();
			}

		}

		return "Question: \n"+ this.question+"\n"
		+ "Answer: \n"+this.answer+"\n"
		+ "tag : "+tag;
	}



}
