package com.example.Internet.Forum.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class represent likes and dislikes through the boolean isLiked 
 * parameter field.
 * 
 * The class contains the id of the user that liked the response and 
 * the id of the response liked.
 * 
 * 
 * @author miche
 *
 */
@Entity
public class Reaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private boolean isLiked;

	private long userId;
	private long responseId;
	
	public Reaction(boolean isLiked, long userId, long responseId) {
		this.isLiked = isLiked;
		this.userId = userId;
		this.responseId = responseId;
	}
	
	public Reaction() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getResponseId() {
		return responseId;
	}

	public void setResponseId(long responseId) {
		this.responseId = responseId;
	}
	
	

	
}
