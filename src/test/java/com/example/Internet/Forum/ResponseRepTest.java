package com.example.Internet.Forum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Internet.Forum.domain.Response;
import com.example.Internet.Forum.domain.ResponseRepository;
import com.example.Internet.Forum.domain.Topic;
import com.example.Internet.Forum.domain.TopicRepository;
import com.example.Internet.Forum.domain.User;
import com.example.Internet.Forum.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ResponseRepTest {


	
	 @Autowired
	 private TopicRepository topicRep;
	 
	 @Autowired
	 private UserRepository userRep;
	 
	 @Autowired
	 private ResponseRepository responseRep;
	 
	 
	 @Test
	 public void createResponses() {
		 
		User admin = new User(
				"admin", 
				"$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", 
				"admin@email.com",
				"ADMIN");
		
		userRep.save(admin);
			
		Topic t1 = new Topic(
				"t1",
				new Date().getTime(),
				1,
				admin
				);
		topicRep.save(t1);
		
		topicRep.save(new Topic(
				"t2",
				new Date().getTime(),
				2,
				admin
				));
		
		topicRep.save(new Topic(
				"t3",
				new Date().getTime(),
				3,
				admin
				));
		

		responseRep.save(new Response(
				"r1",
				new Date().getTime(),
				0,
				0,
				admin,
				t1
				));
		
		responseRep.save(new Response(
				"r2",
				new Date().getTime(),
				0,
				0,
				admin,
				t1
				));
		
		responseRep.save(new Response(
				"r3",
				new Date().getTime(),
				0,
				0,
				admin,
				t1
				));
		 
	 }
	 

	 
	 @Test
	 public void findAll() {
		 
		 createResponses();
		 
		 List<Response> responseList = (List<Response>) responseRep.findAll();
		 assertThat(responseList).hasSize(3);
		 
		 
	 }
	 

	 
	 @Test
	 public void findById() {
		 
		 User admin = new User(
					"admin", 
					"$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", 
					"admin@email.com",
					"ADMIN");
		 
		 userRep.save(admin);
		 
		 Topic t1 = new Topic(
				"t1",
				new Date().getTime(),
				1,
				admin
				);
		
		topicRep.save(t1);
		 
		 Response responseToSave = new Response(
					"r1",
					new Date().getTime(),
					0,
					0,
					admin,
					t1
					);
		 
		 Response responseSaved = responseRep.save(responseToSave);
		 
		 Optional<Response> oResponseFound = responseRep.findById(responseSaved.getId());
		 
		 
		 assertThat(oResponseFound).isPresent();
		 
		 Response responseFound;
		 
		 if(oResponseFound.isPresent()) {
			 responseFound = oResponseFound.get();
			 
			 assertThat(responseFound).isEqualTo(responseToSave);
		 }
		
		
	 }
	 
	 @Test
	 public void findByTopic() {
		 
		 User admin = new User(
					"admin", 
					"$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", 
					"admin@email.com",
					"ADMIN");
		 
		 userRep.save(admin);
		 
		 Topic t1 = new Topic(
				"t1",
				new Date().getTime(),
				1,
				admin
				);
		
		topicRep.save(t1);
		
		 Response r1 = new Response(
					"r1",
					new Date().getTime(),
					0,
					0,
					admin,
					t1
					);
		 
		 Response r2 = new Response(
					"r2",
					new Date().getTime(),
					0,
					0,
					admin,
					t1
					);
		 
		 responseRep.save(r1);
		 responseRep.save(r2);
		 
		List<Response> responsesFound = responseRep.findByTopic(t1);
		
		assertThat(responsesFound).hasSize(2);
		
		assertThat(responsesFound).contains(r1);
		assertThat(responsesFound).contains(r2);
	 }
	

	 
	 
	 @Test
	 public void delete() {
		
		 createResponses();
		 
		 List<Response> responseList = (List<Response>) responseRep.findAll();
		 assertThat(responseList).hasSize(3);
		 
		 responseRep.delete(responseList.get(0));
		 
		 List<Response> responseListReduced = (List<Response>) responseRep.findAll();
		 assertThat(responseListReduced).hasSize(2);
		 
	 }
	 
	
}
