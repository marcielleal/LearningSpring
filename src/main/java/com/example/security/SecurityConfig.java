//package com.example.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableGlobalMethodSecurity(securedEnabled=true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("java").password("{noop}123").roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN", "USER");
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//			.antMatchers("/tasks").hasAnyRole("USER")
//			.anyRequest()
//			.authenticated()
//			.and().formLogin().permitAll()
//			.and().logout().permitAll();
//		http.csrf().disable();
//	}
//}
