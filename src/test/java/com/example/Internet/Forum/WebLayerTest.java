package com.example.Internet.Forum;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class WebLayerTest {
	
	
	@Autowired
	 private MockMvc mockMvc;
	
	
	@Test
	 public void testLogin() throws Exception {
		this.mockMvc.perform(get("/login"))
		.andExpect(status().isOk());
	 }
	
	@Test
	 public void testTopics() throws Exception {
		this.mockMvc.perform(get("/topics"))
		.andExpect(status().isOk());
	 }
	
	@Test
	 public void testTopicDetails() throws Exception {
		this.mockMvc.perform(get("/topics/1"))
		.andExpect(status().isOk());
	 }
	 
	@Test
	 public void testTopicCreation() throws Exception {
		this.mockMvc.perform(get("/createTopic"))
		.andExpect(status().is3xxRedirection());
	 }
	
	@Test
	 public void testTopicEdition() throws Exception {
		this.mockMvc.perform(get("/topics/edit/1"))
		.andExpect(status().is3xxRedirection());
	 }
	
	@Test
	 public void testMessageCreation() throws Exception {
		this.mockMvc.perform(get("/createTopic"))
		.andExpect(status().is3xxRedirection());
	 }
	
	@Test
	 public void testMessageEdition() throws Exception {
		this.mockMvc.perform(get("/topics/response/edit/1"))
		.andExpect(status().is3xxRedirection());
	 }
	
	
}
