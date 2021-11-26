package com.example.Internet.Forum.domain;

import org.springframework.data.repository.CrudRepository;

public interface ReactionRepository extends CrudRepository<Reaction, Long>{

	Reaction findByResponseIdAndUserId(long responseId, long userId);
	
}
