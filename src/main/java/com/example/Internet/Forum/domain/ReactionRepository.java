package com.example.Internet.Forum.domain;

import org.springframework.data.repository.CrudRepository;

public interface LikeDislikeRepository extends CrudRepository<LikeDislike, Long>{

	LikeDislike findByResponseIdAndUserId(long responseId, long userId);
	
}
