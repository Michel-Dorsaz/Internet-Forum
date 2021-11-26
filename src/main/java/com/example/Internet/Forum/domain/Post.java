package com.example.Internet.Forum.domain;


/**
 * This interface is used to combinate a Topic and a Response for search
 * purpose like the BoyerMooreHorspoolSearch 
 * {@link com.example.Internet.Forum.domain.Toolset#BoyerMooreHorspoolSearch()}
 * 
 * @see com.example.Internet.Forum.domain.Toolset
 * 
 * @author miche
 *
 */
public interface Post {

	public boolean isTopic();
	public boolean isResponse();
	public String getContent();

}
