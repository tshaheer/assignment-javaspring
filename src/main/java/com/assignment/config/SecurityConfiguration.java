package com.assignment.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.assignment.security.jwt.JWTConfigurer;
import com.assignment.security.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final TokenProvider tokenProvider;

	public SecurityConfiguration(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        http
            .csrf()
            .disable()
            .exceptionHandling()
            .accessDeniedHandler(new AccessDeniedHandler() {
				@Override
				public void handle(HttpServletRequest request, HttpServletResponse response,
						AccessDeniedException accessDeniedException) throws IOException, ServletException {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				}
			})
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/v1/authenticate").permitAll()
            .antMatchers("/v1/**").authenticated()
        .and()
            .httpBasic()
        .and()
            .apply(securityConfigurerAdapter());
        // @formatter:on
	}

	private JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(tokenProvider);
	}
}
