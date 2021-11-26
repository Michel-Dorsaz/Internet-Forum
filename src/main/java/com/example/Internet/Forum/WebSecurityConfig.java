package com.example.Internet.Forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
				
		http
		 .authorizeRequests()
		 .antMatchers("/topics*").permitAll()
		 .and()
		 .formLogin()
		 .loginPage("/login")
		 	//	Changed from logging with username to logging 
		 	//	with email (username still exist)
		 .usernameParameter("email")  
		 .defaultSuccessUrl("/topics", false)
		 .permitAll()
		 .and()
		 .logout()
		 .logoutSuccessUrl("/topics");
		 
	}
	

	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	 auth.userDetailsService(userDetailsService).passwordEncoder(new
	 BCryptPasswordEncoder());
	}
}
