package com.example.Internet.Forum.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class Topic implements Post, Comparable<Topic>{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String title;
	private long date;
	private int nbParticipants;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;
	
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "topic")
	private List<Response> responses;

	
	public Topic() {
		
	}
	
	


	public Topic(String title, long date, int nbParticipants, User author) {
		this.title = title;
		this.date = date;
		this.nbParticipants = nbParticipants;
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


	public long getDateLong() {
		return this.date;
	}
	
	public String getDate() {
 
	    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");  

		
		return formatter.format(date);
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


	/**
	 * Topics are sorted by date, latest on top.
	 */
	@Override
	public int compareTo(Topic o) {
		
		return (int) (o.getDateLong()-this.getDateLong());
	}




	@Override
	public String getContent() {
		
		return title;
	}




	@Override
	public boolean isTopic() {
		
		return true;
	}




	@Override
	public boolean isResponse() {
		
		return false;
	}


	
}
