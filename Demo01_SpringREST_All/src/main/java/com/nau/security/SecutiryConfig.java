package com.nau.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecutiryConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemory = auth.inMemoryAuthentication();
		
		inMemory.withUser("chandan")
		.password(passwordEncoder().encode("chandan"))
		.roles("ADMIN")
		.and()
		.withUser("tim")
		.password(passwordEncoder().encode("tim123"))
		.roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
		 .authorizeHttpRequests()
		 .antMatchers("/infybank/customers/**")
		 .hasAnyRole("ADMIN")
		 .anyRequest().authenticated()
		 .and()
		 .httpBasic();
		 
		 http.csrf().disable();
	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
