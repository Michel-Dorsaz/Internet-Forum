package com.example.Internet.Forum.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Internet.Forum.domain.Response;
import com.example.Internet.Forum.domain.ResponseRepository;
import com.example.Internet.Forum.domain.Topic;
import com.example.Internet.Forum.domain.TopicRepository;
import com.example.Internet.Forum.domain.User;
import com.example.Internet.Forum.domain.UserRepository;

@Controller
public class TopicController {
	
	
	@Bean
	public CommandLineRunner demo(
			TopicRepository topicRep, 
			ResponseRepository responseRep,
			UserRepository userRep) {
		return (args) -> {

		User admin = new User(
					"admin", 
					"$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", 
					"user@email.com",
					"USER");
			
		User michel = new User(
				"michel", 
				"$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", 
				"user@email.com",
				"USER");	
		User user = new User(
				"user", 
				"$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", 
				"user@email.com",
				"USER");
		
		userRep.save(admin);
		userRep.save(michel);
		userRep.save(user);
		
		
		Topic topic1 = new Topic(
				"Welcome to Finlande",
				Calendar.getInstance().getTimeInMillis(),
				1,
				0,
				admin
				);
		
		Topic topic2 = new Topic(
				"Looking for a room",
				Calendar.getInstance().getTimeInMillis(),
				1,
				0,
				user
				);
		
		topicRep.save(topic1);
		topicRep.save(topic2);
		
		Response res1 = new Response(
				"Hello, I am looking for a cheap room ! My budget is "
				+ "500€/monthly. Any good leak ?",
				new Date().getTime(),
				0,
				0,
				user,
				topic2
				);
		
		Response res2 = new Response(
				"Welcome to Finlande ! In this forum you can find many usefull "
				+ "informations ! And if you can't find the one you need, "
				+ "then just ask for it ! People will answer you in no time ;)",
				new Date().getTime(),
				20,
				0,
				admin,
				topic1
				);
		
		responseRep.save(res1);
		//responseRep.save(res2);
		
		};
	}

	@Autowired
	private TopicRepository topicRep;

	@Autowired
	private ResponseRepository responseRep;
	
	@Autowired
	private UserRepository userRep;

	@GetMapping("/login")
	public String login(Model model) {

		return "login";
	}

	https://stackoverflow.com/questions/46330830/how-to-use-thymeleaf-to-include-html-file-into-another-html-file/53717810
		
	@GetMapping("/topics")
	public String topiclist(Model model) {

		Iterable<Topic> topics = topicRep.findAll();

		model.addAttribute("topics", topics);
		
		return "topics";
	}
	
	@GetMapping("/topics/{id}")
	public String topicdetail(@PathVariable("id") Long topicId, Model model) {
			
		
		Optional topic = topicRep.findById(topicId);
		
		if(topic.isPresent()) {
			
			List<Response> responses = responseRep.findByTopic((Topic) topic.get());
			
			model.addAttribute("responses", responses);
			model.addAttribute("topic", (Topic) topic.get());
			
			return "topicDetail";
		}
		

		return "login";
	}


	/*
	@GetMapping("/delete/{id}")

	public String deleteBook(@PathVariable("id") Long bookId, Model model) {

		bookRepository.deleteById(bookId);

		return "redirect:../booklist";
	}

	@GetMapping("/add")
	public String addBook(Model model) {

		model.addAttribute("book", new Book());
		model.addAttribute("categories", categoryRepository.findAll());

		return "addbook";
	}

	@GetMapping("/edit/{id}")
	public String editBook(@PathVariable("id") Long bookId, Model model) {

		Optional<Book> book = bookRepository.findById(bookId);

		if (book.isEmpty())
			return "redirect:../booklist";

		model.addAttribute("book", book.get());
		model.addAttribute("categories", categoryRepository.findAll());

		return "editbook";
	}

	@PostMapping("/save")
	public String saveBook(Book book) {

		bookRepository.save(book);

		return "redirect:booklist";
	}
	*/
}
