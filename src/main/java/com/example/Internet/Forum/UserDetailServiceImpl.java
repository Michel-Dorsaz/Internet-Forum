package com.example.Internet.Forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Internet.Forum.domain.LoggedUser;
import com.example.Internet.Forum.domain.User;
import com.example.Internet.Forum.domain.UserRepository;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	private final UserRepository repository;

	@Autowired
	public UserDetailServiceImpl(UserRepository userRepository) {
		this.repository = userRepository;
	}
	

	@Override
	public LoggedUser loadUserByUsername(String email) throws UsernameNotFoundException {
		User curruser = repository.findByEmail(email);	
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(curruser.getUsername(), curruser.getPasswordHash(),
				AuthorityUtils.createAuthorityList(curruser.getRole()));
		
		LoggedUser user = new LoggedUser(userDetails, curruser);
		return user;
	}
}
