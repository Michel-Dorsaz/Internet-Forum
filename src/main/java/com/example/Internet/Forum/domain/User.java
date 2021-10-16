package com.example.Internet.Forum.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	 

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)	 
	 @Column(name = "id", nullable = false, updatable = false)
	 private Long id;
	 
	 @Column(name = "username", nullable = false, unique = true)
	 private String username;
	 
	 @Column(name = "password", nullable = false)
	 private String passwordHash;
	 
	 @Column(name = "email", nullable = false)
	 private String email;
	 
	 @Column(name = "role", nullable = false)
	 private String role;
	 
	 
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "author")
	private List<Topic> topics;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "author")
	private List<Response> responses;
	 
	 
	 public User() {
		 
	 }
	 
	 
	 public User(String username, String password, String email, String role) {
		 this.username = username;
		 this.passwordHash = password;
		 this.email = email;
		 this.role = role;
	 }
	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public List<Topic> getTopics() {
		return topics;
	}


	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}


	public List<Response> getResponses() {
		return responses;
	}


	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	
	
	 
	 
	 
	
}
