package org.moviematchers.moviematch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moviematchers.moviematch.dto.AuthorizationToken;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JWTAuthorizationTokenServiceTest {
    @Mock
    private JwtEncoder JwtEncoder;
    private JWTAuthorizationTokenService underTest;
    @BeforeEach
    void setUp() {
        underTest = new JWTAuthorizationTokenService(JwtEncoder);
    }

    @Test
    void canGenerateToken() {
        // given
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());

        Jwt jwtMock = mock(Jwt.class);
        when(jwtMock.getTokenValue()).thenReturn("mocked_token_value");
        when(JwtEncoder.encode(any())).thenReturn(jwtMock);

        // when
        AuthorizationToken result = underTest.generateToken(authentication, 1, ChronoUnit.HALF_DAYS);

        // then
        assertNotNull(result);
        Instant now = Instant.now();
        Instant expectedExpiration = now.plus(1, ChronoUnit.HALF_DAYS);
        assertTrue(result.getIssuedTime().isBefore(result.getExpirationTime()));
        assertEquals("mocked_token_value", result.getValue());
    }
}