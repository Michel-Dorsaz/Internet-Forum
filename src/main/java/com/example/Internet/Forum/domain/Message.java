package com.example.Internet.Forum.domain;


/**
 * A simple DTO message that can be used to send and retrieve information
 * from HTML using Thymeleaf.
 * 
 * @author miche
 *
 */
public class Message {
	
	private String content;

	public Message() {
		content = "";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
