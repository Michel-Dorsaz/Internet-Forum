package com.example.Internet.Forum;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Internet.Forum.domain.Post;
import com.example.Internet.Forum.domain.ToolSet;
import com.example.Internet.Forum.domain.Topic;
import com.example.Internet.Forum.domain.User;


@SpringBootTest
public class ToolSetTest {
	
	
	
	@Test
	public void BoyerMooreHorspoolSearch() {
		
		ToolSet toolSet = new ToolSet();
		
		String[] matchingFilters = {
				"filter"
		};
		
		String[] NotMatchingFilters = {
				"Filter", "FILTER", "FiLtEr",
				"fil ter", "fliter", "Filtair", "retlif",
				"filter are found", "the words filter"
		};
		
		String[] matchingLowerCaseFilters = {
				"Filter", "FILTER", "FiLtEr",
		};
		
		String[] textMatching = {
				"filter is found here !",
				"We want to find the word filter here !",
				"Here we find filter",
				"We can find filters too !",
				"Or unfilter",
				"Or filtering",
				"Or unfiltering",
				
		};

		
		for(String t : textMatching) {
			
			for(String s : matchingFilters) {
				int positionFound = toolSet.BoyerMooreHorspoolSearch(t, s);
				assertTrue(positionFound != -1);
			}
			
			for(String s : NotMatchingFilters) {
				int positionFound = toolSet.BoyerMooreHorspoolSearch(t, s);
				assertTrue(positionFound == -1);
			}
			
			for(String s : matchingLowerCaseFilters) {
				int positionFound = toolSet.BoyerMooreHorspoolSearch(t.toLowerCase(), s.toLowerCase());
				assertTrue(positionFound != -1);
			}
		}
		
		
	}
	
	
	@Test
	public void filter() {
		
		ToolSet toolSet = new ToolSet();
		
		Topic matching1 = new Topic(
				"Filter is found here in upperCase!",
				new Date().getTime(),
				0,
				new User()
				);
		
		Topic matching2 = new Topic(
				"the word filter is found here !",
				new Date().getTime(),
				0,
				new User()
				);
		
		Topic unmatching = new Topic(
				"the word is not found here !",
				new Date().getTime(),
				0,
				new User()
				);
		
		
		List<Topic> topics = new ArrayList();
		
		topics.add(matching1);
		topics.add(matching2);
		topics.add(unmatching);
		
		List<Topic> topicsFound = toolSet.filter(topics, "filter");
		
		assertTrue(topicsFound.contains(matching1));
		assertTrue(topicsFound.contains(matching2));
		assertFalse(topicsFound.contains(unmatching));
		
		
	}
}
