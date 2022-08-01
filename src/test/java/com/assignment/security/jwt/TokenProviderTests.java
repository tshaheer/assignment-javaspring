package com.assignment.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import com.assignment.dao.UserDao;
import com.assignment.dao.impl.UserDaoImpl;
import com.assignment.security.AuthoritiesConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

class TokenProviderTests {

    private static final long ONE_MINUTE = 60000;

    private Key key;
    private TokenProvider tokenProvider;

    @BeforeEach
    public void setup() {
        String base64Secret = "fd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8";
        UserDao userDao = Mockito.mock(UserDaoImpl.class);
        tokenProvider = new TokenProvider(userDao);
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));

        ReflectionTestUtils.setField(tokenProvider, "key", key);
        ReflectionTestUtils.setField(tokenProvider, "tokenValidityInMilliseconds", ONE_MINUTE);
    }

    @Test
    void givenInvalidSignatureJWT_whenValidateToken_thenReturnFalse() {
        boolean isTokenValid = tokenProvider.validateToken(createTokenWithDifferentSignature());
        assertThat(isTokenValid).isFalse();
    }

    @Test
    void givenMalformedJWT_whenValidateToken_thenReturnFalse() {
        Authentication authentication = createAuthentication();
        String token = tokenProvider.createToken(authentication);
        String invalidToken = token.substring(1);
        boolean isTokenValid = tokenProvider.validateToken(invalidToken);

        assertThat(isTokenValid).isFalse();
    }

    @Test
    void givenExpiredJWT_whenValidateToken_thenReturnFalse() {
        ReflectionTestUtils.setField(tokenProvider, "tokenValidityInMilliseconds", -ONE_MINUTE);
        Authentication authentication = createAuthentication();
        String token = tokenProvider.createToken(authentication);
        boolean isTokenValid = tokenProvider.validateToken(token);

        assertThat(isTokenValid).isFalse();
    }

    @Test
    void givenUnsupportedJWT_whenValidateToken_thenReturnFalse() {
        String unsupportedToken = createUnsupportedToken();
        boolean isTokenValid = tokenProvider.validateToken(unsupportedToken);
        assertThat(isTokenValid).isFalse();
    }

    @Test
    void givenInvalidJWT_whenValidateToken_thenReturnFalse() {
        boolean isTokenValid = tokenProvider.validateToken("");
        assertThat(isTokenValid).isFalse();
    }

    private Authentication createAuthentication() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        return new UsernamePasswordAuthenticationToken("anonymous", "anonymous", authorities);
    }

    private String createUnsupportedToken() {
        return Jwts.builder().setPayload("payload").signWith(key, SignatureAlgorithm.HS512).compact();
    }

    private String createTokenWithDifferentSignature() {
        Key otherKey = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode("Xfd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8")
        );

        return Jwts
            .builder()
            .setSubject("anonymous")
            .signWith(otherKey, SignatureAlgorithm.HS512)
            .setExpiration(new Date(new Date().getTime() + ONE_MINUTE))
            .compact();
    }
}