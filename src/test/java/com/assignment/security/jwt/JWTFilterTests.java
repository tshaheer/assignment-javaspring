package com.assignment.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import com.assignment.dao.UserDao;
import com.assignment.dao.impl.UserDaoImpl;
import com.assignment.security.AuthoritiesConstants;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

class JWTFilterTests {

    private TokenProvider tokenProvider;

    private JWTFilter jwtFilter;

    @BeforeEach
    public void setup() {
        String base64Secret = "bXktc2VjcmV0LWtleS13aGljaC1zaG91bGQtYmUtY2hhbmdlZC1pbi1wcm9kdWN0aW9uLWFuZC1iZS1iYXNlNjQtZW5jb2RlZAo=";
        UserDao userDao = Mockito.mock(UserDaoImpl.class);
        tokenProvider = new TokenProvider(userDao);
        ReflectionTestUtils.setField(tokenProvider, "key", Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)));
        ReflectionTestUtils.setField(tokenProvider, "tokenValidityInMilliseconds", 60000);
        jwtFilter = new JWTFilter(tokenProvider);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void givenUsernamePassword_whenTestJWTFilter_thenAuthentiated() throws Exception {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            "user",
            "user",
            Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))
        );
        String jwt = tokenProvider.createToken(authentication);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("user");
        assertThat(SecurityContextHolder.getContext().getAuthentication().getCredentials()).hasToString(jwt);
    }

    @Test
    void givenInvalidToken_whenTestJWTFilter_thenMissingAuthorization() throws Exception {
        String jwt = "wrong_jwt";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void givenNoTokenHeader_whenJWTFilter_thenMissingAuthorization() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void givenMissingToken_whenJWTFilter_thenMissingAuthorization() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer ");
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void testJWTFilterWrongScheme() throws Exception {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            "test-user",
            "test-password",
            Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))
        );
        String jwt = tokenProvider.createToken(authentication);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Basic " + jwt);
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
    
    @Test
    void givenExpiredTokenLogoutUrl_whenTestJWTFilter_thenLogout() throws Exception {
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY1OTQzMTYwMH0.dFqbMRgy1JqklyRIqbVSeRgOk7H4gIAi6ypp8P09jXQgrQMZDcEK38qPRUfy1hW9lgEsGeRIzFI_0gXU9RUONw";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        request.setRequestURI("/api/v1/logout");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}