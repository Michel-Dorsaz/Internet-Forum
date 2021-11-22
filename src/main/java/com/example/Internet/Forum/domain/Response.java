package com.example.Internet.Forum.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Response implements Post, Comparable<Response>{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String content;
	private long date;
	private int nbLike, nbDislike;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;
	
	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	

	public Response() {
		
	}
	
	


	public Response(String content, long date, int nbLike, int nbDislike, User author, Topic topic) {
		this.content = content;
		this.date = date;
		this.nbLike = nbLike;
		this.nbDislike = nbDislike;
		this.author = author;
		this.topic = topic;
	}




	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public long getDateLong() {
		return date;
	}

	public String getDate() {
		
	    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");  
		
		return formatter.format(date);
	}


	public void setDate(long date) {
		this.date = date;
	}


	public int getNbLike() {
		return nbLike;
	}


	public void setNbLike(int nbLike) {
		this.nbLike = nbLike;
	}


	public int getNbDislike() {
		return nbDislike;
	}


	public void setNbDislike(int nbDislike) {
		this.nbDislike = nbDislike;
	}


	public User getAuthor() {
		return author;
	}


	public void setAuthor(User author) {
		this.author = author;
	}


	public Topic getTopic() {
		return topic;
	}


	public void setTopic(Topic topic) {
		this.topic = topic;
	}



	@Override
	public int compareTo(Response o) {
		
		return (int) (this.getDateLong()-o.getDateLong());
	}




	@Override
	public boolean isTopic() {
		
		return false;
	}




	@Override
	public boolean isResponse() {
		
		return true;
	}
	
	
	
	
}
