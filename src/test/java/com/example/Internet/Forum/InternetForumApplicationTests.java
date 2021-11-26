package com.example.Internet.Forum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Internet.Forum.web.TopicController;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InternetForumApplicationTests {

	@Autowired
	 private TopicController controller;
	
	@Test
	void contextLoads() {
		 assertThat(controller).isNotNull();
	}
	
	

}
