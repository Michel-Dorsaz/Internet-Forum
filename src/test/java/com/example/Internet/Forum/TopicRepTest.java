package com.example.Internet.Forum;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Internet.Forum.domain.Response;
import com.example.Internet.Forum.domain.ResponseRepository;
import com.example.Internet.Forum.domain.Topic;
import com.example.Internet.Forum.domain.TopicRepository;
import com.example.Internet.Forum.domain.User;
import com.example.Internet.Forum.domain.UserRepository;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TopicRepTest {
	
	 @Autowired
	 private TopicRepository topicRep;
	 
	 @Autowired
	 private UserRepository userRep;
	 
	 
	 @Test
	 public void createTopics() {
		 
		User admin = new User(
				"admin", 
				"$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", 
				"admin@email.com",
				"ADMIN");
		
		userRep.save(admin);
				

		topicRep.save(new Topic(
				"t1",
				new Date().getTime(),
				1,
				admin
				));
		
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
		
		 
	 }
	 
	 
	 @Test
	 public void findAll() {
		 
		 createTopics();
		 
		 List<Topic> topicList = (List<Topic>) topicRep.findAll();
		 assertThat(topicList).hasSize(3);
		 
		 
	 }
	 

	 @Test
	 public void findById() {
		 
		 User admin = new User(
					"admin", 
					"$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", 
					"admin@email.com",
					"ADMIN");
		 
		 Topic topicToSave = new Topic(
					"t1",
					new Date().getTime(),
					1,
					admin
					);
		 
		 Topic topicSaved = topicRep.save(topicToSave);
		 
		 Optional<Topic> oTopicFound = topicRep.findById(topicSaved.getId());
		 
		 
		 assertThat(oTopicFound).isPresent();
		 
		 Topic topicFound;
		 
		 if(oTopicFound.isPresent()) {
			 topicFound = oTopicFound.get();
			 
			 assertThat(topicFound).isEqualTo(topicToSave);
		 }
		
		
	 }
	
	 @Test
	 public void delete() {
		
		 createTopics();
		 
		 List<Topic> topicList = (List<Topic>) topicRep.findAll();
		 assertThat(topicList).hasSize(3);
		 	 
		 
		 topicRep.delete(topicList.get(0));
		 
		 List<Topic> topicListReduced = (List<Topic>) topicRep.findAll();
		 assertThat(topicListReduced).hasSize(2);

		 
	 }
	 
}
