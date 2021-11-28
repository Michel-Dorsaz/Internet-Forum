package com.example.Internet.Forum.web;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Internet.Forum.domain.Reaction;
import com.example.Internet.Forum.domain.ReactionRepository;
import com.example.Internet.Forum.domain.LoggedUser;
import com.example.Internet.Forum.domain.Response;
import com.example.Internet.Forum.domain.ResponseRepository;
import com.example.Internet.Forum.domain.Topic;
import com.example.Internet.Forum.domain.TopicRepository;
import com.example.Internet.Forum.domain.User;
import com.example.Internet.Forum.domain.UserRepository;
import com.example.Internet.Forum.domain.Message;
import com.example.Internet.Forum.domain.ToolSet;

@Controller
public class TopicController {

	@Bean
	public CommandLineRunner demo(TopicRepository topicRep, ResponseRepository responseRep, UserRepository userRep) {
		return (args) -> {

			User admin = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C",
					"admin@email.com", "ADMIN");

			User michel = new User("michel", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6",
					"michel@email.com", "USER");
			User user = new User("Jojo", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6",
					"user@email.com", "USER");

			userRep.save(admin);
			userRep.save(michel);
			userRep.save(user);

			Topic topic1 = new Topic("Welcome to Finlande", new Date().getTime(), 2, admin);

			Date d = new Date();
			d.setYear(100);

			Topic topic2 = new Topic("Looking for a room", d.getTime(), 2, user);

			topicRep.save(topic1);
			topicRep.save(topic2);

			Response res1 = new Response(
					"Hello, I am looking for a cheap room ! My budget is " + "500€/monthly. Any good leak ?",
					new Date().getTime(), 0, 0, user, topic2);

			Response res11 = new Response(
					"Have you looked at HOAS ? They offer cheap rooms for students. "
							+ "Available rooms are limited so you should apply as soon as you can !"
							+ "You can apply at https://hoas.fi/en/applicants/exchange-students/",
					new Date().getTime(), 12, 0, admin, topic2);

			Response res2 = new Response(
					"Welcome to Finlande ! In this forum you can find many usefull "
							+ "informations ! And if you can't find the one you need, "
							+ "then just ask for it ! People will answer you in no time ;)",
					new Date().getTime(), 20, 0, admin, topic1);

			Response res21 = new Response("Very usefull forum 👍", new Date().getTime(), 4, 0, user, topic1);

			Response res22 = new Response("I was looking for that ! ♥", new Date().getTime(), 1, 0, user, topic1);

			responseRep.save(res1);
			responseRep.save(res11);
			responseRep.save(res2);
			responseRep.save(res21);
			responseRep.save(res22);

		};
	}

	@Autowired
	private TopicRepository topicRep;

	@Autowired
	private ResponseRepository responseRep;

	@Autowired
	private UserRepository userRep;

	@Autowired
	private ReactionRepository reactionRep;

	
	@GetMapping("/")
	public String home(Model model) {

		return "redirect:/topics";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {

		return "login";
	}

	@GetMapping("/topics")
	public String topiclist(@RequestParam(value = "content", required = false) String filter, Model model,
			Message searchInput2) {

		List<Topic> topics = (List<Topic>) topicRep.findAll();

		topics = new ToolSet().filter(topics, filter);
		Collections.sort((List<Topic>) topics);

		Message searchInput = new Message();

		if (filter != null && filter != "")
			searchInput.setContent(filter);

		model.addAttribute("searchInput", searchInput);
		model.addAttribute("topics", topics);

		return "topics";
	}

	@GetMapping("/topics/{id}")
	public String topicdetail(@RequestParam(value = "content", required = false) String filter,
			@RequestParam(value = "liked", required = false) Long responseLiked,
			@RequestParam(value = "disliked", required = false) Long responseDisliked, @PathVariable("id") Long topicId,
			Model model) {

		Optional<Topic> topic = topicRep.findById(topicId);

		if (topic.isPresent()) {

			try {

				if (responseLiked != null) {
					like(responseLiked);
				}
				if (responseDisliked != null) {
					dislike(responseDisliked);
				}
			} catch (ClassCastException e) {

				model.addAttribute("errorMessage", "Error accessing restricted functionality to logged users");
				return "error";
			}

			List<Response> responses = responseRep.findByTopic((Topic) topic.get());

			responses = new ToolSet().filter(responses, filter);

			Collections.sort(responses);

			Message searchInput = new Message();

			if (filter != null && filter != "")
				searchInput.setContent(filter);

			model.addAttribute("responses", responses);
			model.addAttribute("searchInput", searchInput);
			model.addAttribute("responseInput", new Message());
			model.addAttribute("topic", (Topic) topic.get());

			return "topicDetail";
		}

		model.addAttribute("errorMessage", "Error while retrieving topic");
		return "error";
	}

	@PostMapping("/topics/{id}")
	public String topicdetailResponse(@PathVariable("id") Long topicId, Model model, Message input) {

		Optional<Topic> oTopic = topicRep.findById(topicId);

		Topic topic = new Topic();

		if (oTopic.isPresent()) {
			topic = oTopic.get();
		}

		try {

			LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Response newResponse = new Response(input.getContent(), new Date().getTime(), 0, 0, loggedUser.getUser(),
					topic);

			responseRep.save(newResponse);
		} catch (ClassCastException e) {

			model.addAttribute("errorMessage", "Error accessing restricted functionality to logged users");
			return "error";
		} catch (Exception e) {

			model.addAttribute("errorMessage", "Error while saving response");
			return "error";

		}

		List<Response> responses = responseRep.findByTopic(topic);

		model.addAttribute("responses", responses);
		model.addAttribute("searchInput", new Message());
		model.addAttribute("responseInput", new Message());
		model.addAttribute("topic", topic);

		return "topicDetail";

	}

	@GetMapping("/historyBack/{id}")
	public String logout(@PathVariable("id") int id, Model model) {

		model.addAttribute("pagesBack", -id);

		return "historyBack";
	}

	@GetMapping("/createTopic")
	@PreAuthorize("isAuthenticated()")
	public String createTopic(Model model) {

		try {
			LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Topic topic = new Topic();
			topic.setAuthor(loggedUser.getUser());

			model.addAttribute("topic", topic);

			return "editTopic";
		} catch (ClassCastException e) {

			model.addAttribute("errorMessage", "Error accessing restricted functionality to logged users");
			return "error";
		}

	}

	@PostMapping("/saveTopic")
	public String saveTopic(Topic topic) {

		topic.setDate(new Date().getTime());
		topicRep.save(topic);

		return "redirect:topics";
	}

	@PostMapping("/saveEditedResponse")
	public String saveResponse(Response newResponse, Model model) {

		if (newResponse.getContent().length() > 255) {

			model.addAttribute("maxLengthExceeded", true);
			return "editResponse";
		}

		Optional<Response> oResponse = responseRep.findById(newResponse.getId());

		Response response = null;

		if (oResponse.isPresent()) {

			response = oResponse.get();

			response.setContent(newResponse.getContent());
			
			responseRep.save(response);

			return "redirect:topics";
		}
		
		
		model.addAttribute("errorMessage", "Error while trying to retrieve response");
		return "error";

	}

	@GetMapping("/topics/edit/{id}")
	@PreAuthorize("isAuthenticated()")
	public String editTopic(@PathVariable("id") long topicId, Model model) {

		try {
			LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Optional<Topic> oTopic = topicRep.findById(topicId);

			if (oTopic.isPresent()) {

				Topic topic = oTopic.get();

				if (loggedUser.getUser().getId() != topic.getAuthor().getId()
						&& !loggedUser.getUser().getRole().contentEquals("ADMIN")) {

					model.addAttribute("errorMessage", "Error trying to edit topic from someone else");
					return "error";

				} else {

					model.addAttribute("topic", topic);
					return "editTopic";
				}

			} else {
				model.addAttribute("errorMessage", "Error while trying to retrieve topic");
				return "error";
			}

		} catch (ClassCastException e) {

			model.addAttribute("errorMessage", "Error accessing restricted functionality to logged users");
			return "error";
		}
	}

	@GetMapping("/topics/delete/{id}")
	@PreAuthorize("isAuthenticated()")
	public String deleteTopic(@PathVariable("id") long topicId, Model model) {

		try {
			LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Optional<Topic> oTopic = topicRep.findById(topicId);

			if (oTopic.isPresent()) {

				Topic topic = oTopic.get();

				if (!loggedUser.getUser().getRole().contentEquals("ADMIN")) {

					model.addAttribute("errorMessage", "Error accessing restricted functionality to admins");
					return "error";

				} else {

					List<Response> linkedResponses = responseRep.findByTopic(topic);

					responseRep.deleteAll(linkedResponses);
					topicRep.delete(topic);

					return "redirect:../../topics";
				}

			} else {
				model.addAttribute("errorMessage", "Error while trying to retrieve topic");
				return "error";
			}

		} catch (ClassCastException e) {

			model.addAttribute("errorMessage", "Error accessing restricted functionality to admins");
			return "error";
		}
	}

	@GetMapping("/topics/response/edit/{id}")
	@PreAuthorize("isAuthenticated()")
	public String editResponse(@PathVariable("id") long responseId, Model model) {

		try {
			LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Optional<Response> oResponse = responseRep.findById(responseId);

			if (oResponse.isPresent()) {

				Response response = oResponse.get();

				if (loggedUser.getUser().getId() != response.getAuthor().getId()
						&& !loggedUser.getUser().getRole().contentEquals("ADMIN")) {

					model.addAttribute("errorMessage", "Error trying to edit reponse from someone else");
					return "error";

				} else {

					model.addAttribute("response", response);
					return "editResponse";
				}

			} else {
				model.addAttribute("errorMessage", "Error while trying to retrieve response");
				return "error";
			}

		} catch (ClassCastException e) {

			model.addAttribute("errorMessage", "Error accessing restricted functionality to logged users");
			return "error";
		}
	}

	@GetMapping("/topics/response/delete/{id}")
	@PreAuthorize("isAuthenticated()")
	public String deleteResponse(@PathVariable("id") long responseId, Model model) {

		try {
			LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Optional<Response> oResponse = responseRep.findById(responseId);

			if (oResponse.isPresent()) {

				Response response = oResponse.get();

				if (!loggedUser.getUser().getRole().contentEquals("ADMIN")) {

					model.addAttribute("errorMessage", "Error accessing restricted functionality to admins");
					return "error";

				} else {

					responseRep.delete(response);

					model.addAttribute("pagesBack", -1);

					return "historyBack";
				}

			} else {
				model.addAttribute("errorMessage", "Error while trying to retrieve response");
				return "error";
			}

		} catch (ClassCastException e) {

			model.addAttribute("errorMessage", "Error accessing restricted functionality to admins");
			return "error";
		}
	}

	@GetMapping("/createUser")
	public String createUser(Model model) {

		model.addAttribute("user", new User());
		return "createUser";
	}

	@PostMapping("/createUser")
	public String createdUser(User user, Model model) {

		User existingEmail = userRep.findByEmail(user.getEmail());
		User existingUsername = userRep.findByUsername(user.getUsername());

		if (existingEmail != null) {
			model.addAttribute("emailAlreadyExist", true);
		}
		if (existingUsername != null) {
			model.addAttribute("usernameAlreadyExist", true);
		}
		if (existingEmail != null || existingUsername != null) {
			return "createUser";
		} else {

			userRep.save(new User(user.getUsername(), BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt()),
					user.getEmail(), "USER"));

			return "login";
		}

	}

	
	/**
	 * This method is used when a user like a Response.
	 * 
	 * The method check if the user has already liked it and if a dislike need
	 * to be undone.
	 * 
	 * @param responseId
	 * @throws ClassCastException
	 */
	public void like(long responseId) throws ClassCastException {

		try {

			LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Optional<Response> oResponse = responseRep.findById(responseId);

			if (!oResponse.isPresent()) {
				return;
			}

			Response response = oResponse.get();

			Reaction responseElement = reactionRep.findByResponseIdAndUserId(responseId, loggedUser.getUser().getId());

			if (responseElement == null) {
				responseElement = new Reaction(true, loggedUser.getUser().getId(), responseId);

			} else if (responseElement.isLiked()) {
				reactionRep.delete(responseElement);

				response.setNbLike(response.getNbLike() - 1);
				responseRep.save(response);
				return;
			} else if (!responseElement.isLiked()) {
				responseElement.setLiked(true);

				response.setNbDislike(response.getNbDislike() - 1);
			}

			reactionRep.save(responseElement);
			response.setNbLike(response.getNbLike() + 1);
			responseRep.save(response);
		} catch (ClassCastException e) {
			throw e;
		}

	}

	
	/**
	 * This method is used when a user dislike a Response.
	 * 
	 * The method check if the user has already disliked it and if a like need
	 * to be undone.
	 * 
	 * @param responseId
	 * @throws ClassCastException
	 */
	public void dislike(long responseId) throws ClassCastException {

		try {

			LoggedUser loggedUser = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Optional<Response> oResponse = responseRep.findById(responseId);

			if (!oResponse.isPresent()) {
				return;
			}

			Response response = oResponse.get();

			Reaction responseElement = reactionRep.findByResponseIdAndUserId(responseId, loggedUser.getUser().getId());

			if (responseElement == null) {
				responseElement = new Reaction(false, loggedUser.getUser().getId(), responseId);

			} else if (!responseElement.isLiked()) {
				reactionRep.delete(responseElement);

				response.setNbDislike(response.getNbDislike() - 1);
				responseRep.save(response);
				return;
			} else if (responseElement.isLiked()) {
				responseElement.setLiked(false);

				response.setNbLike(response.getNbLike() - 1);
			}

			reactionRep.save(responseElement);
			response.setNbDislike(response.getNbDislike() + 1);
			responseRep.save(response);

		} catch (ClassCastException e) {
			throw e;
		}
	}

}
