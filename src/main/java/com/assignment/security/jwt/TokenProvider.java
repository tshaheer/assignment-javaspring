package com.assignment.security.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.assignment.dao.UserDao;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class TokenProvider {

	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	private static final String AUTHORITIES_KEY = "auth";

	private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";

	private Key key;

	private JwtParser jwtParser;

	private long tokenValidityInMilliseconds;

	private String base64Sec;

	private final UserDao userDao;

	public TokenProvider(UserDao userDao) {
		// 5 minute
		this.tokenValidityInMilliseconds = 300000;
		this.base64Sec = "bXktc2VjcmV0LWtleS13aGljaC1zaG91bGQtYmUtY2hhbmdlZC1pbi1wcm9kdWN0aW9uLWFuZC1iZS1iYXNlNjQtZW5jb2RlZAo=";
		byte[] keyBytes = Decoders.BASE64.decode(this.base64Sec);
		key = Keys.hmacShaKeyFor(keyBytes);
		jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
		this.userDao = userDao;
	}

	public String createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);
		String token = Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();
		userDao.addToken(authentication.getName(), token);
		return token;
	}

	public Authentication getAuthentication(String token) {
		Claims claims = jwtParser.parseClaimsJws(token).getBody();

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).filter(auth -> !auth.trim().isEmpty())
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String authToken) {
		try {
			jwtParser.parseClaimsJws(authToken);
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
			log.debug(INVALID_JWT_TOKEN);
			log.trace(INVALID_JWT_TOKEN, e);
		} catch (IllegalArgumentException e) {
			log.error("Token validation error {}", e.getMessage());
		}
		return false;
	}
	
	public String getUsername(String token) {
		try {
			jwtParser.parseClaimsJws(token).getBody();
		}catch (ExpiredJwtException  e) {
			return e.getClaims().getSubject();
		}
		return null;
	}

	public void removeSession(String username) {
		userDao.removeToken(username);
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}
