package com.dev.lima.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };
	
	private static final String[] PUBLIC_MATCHERS_GET = { "/persons/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS).permitAll()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
				.anyRequest().authenticated()
			.and()
				.httpBasic()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.csrf().disable();
		
		http.headers().frameOptions().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		builder
			.inMemoryAuthentication()
			.passwordEncoder(encoder)
			.withUser("israel").password(encoder.encode("123"))
				.roles("ADM");
	}

}
