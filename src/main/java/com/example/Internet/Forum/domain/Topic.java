package com.example.Internet.Forum.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Topic {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String title;
	private long date;
	private int nbParticipants, nbFolowers;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;
	
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "topic")
	private List<Response> responses;

	
	public Topic() {
		
	}
	
	


	public Topic(String title, long date, int nbParticipants, int nbFolowers, User author) {
		this.title = title;
		this.date = date;
		this.nbParticipants = nbParticipants;
		this.nbFolowers = nbFolowers;
		this.author = author;
	}




	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDate() {
		
		Calendar.getInstance().setTimeInMillis(date);
		
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
		+ "." 
		+ (Calendar.getInstance().get(Calendar.MONTH)+1)
		+ "." 
		+ Calendar.getInstance().get(Calendar.YEAR);
	}


	public void setDate(long date) {
		this.date = date;
	}


	public int getNbParticipants() {
		return nbParticipants;
	}


	public void setNbParticipants(int nbParticipants) {
		this.nbParticipants = nbParticipants;
	}


	public int getNbFolowers() {
		return nbFolowers;
	}


	public void setNbFolowers(int nbFolowers) {
		this.nbFolowers = nbFolowers;
	}


	public List<Response> getResponses() {
		return responses;
	}


	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}


	public User getAuthor() {
		return author;
	}


	public void setAuthor(User author) {
		this.author = author;
	}
	
	
	
}
