package com.example.Internet.Forum;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.assertj.core.api.Assertions.assertThat;

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

import com.example.Internet.Forum.domain.Reaction;
import com.example.Internet.Forum.domain.ReactionRepository;
import com.example.Internet.Forum.domain.ResponseRepository;
import com.example.Internet.Forum.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ReactionRepTest {
	
	@Autowired
	 private ReactionRepository reactionRep;
	
	@Test
	public void createReactions() {
		
		reactionRep.save(new Reaction(true, 1, 1));
		reactionRep.save(new Reaction(false, 2, 2));
		reactionRep.save(new Reaction(true, 3, 3));
	
		
	}
	
	@Test
	public void findByResponsIdAndUserId() {
		
		createReactions();
		
		Reaction r1 = reactionRep.findByResponseIdAndUserId(1, 1);
		Reaction r2 = reactionRep.findByResponseIdAndUserId(2, 2);
		Reaction r3 = reactionRep.findByResponseIdAndUserId(3, 3);
		
		assertThat(r1.isLiked()).isTrue();
		assertThat(r2.isLiked()).isFalse();
		assertThat(r3.isLiked()).isTrue();
		
	}
	
	@Test
	public void delete() {
		
		createReactions();
		
		Reaction reaction = reactionRep.findByResponseIdAndUserId(1, 1);

		assertThat(reaction).isNotNull();
		
		reactionRep.delete(reaction);
		
		reaction = reactionRep.findByResponseIdAndUserId(1, 1);

		assertThat(reaction).isNull();
		
	}
	

	 
	
}
