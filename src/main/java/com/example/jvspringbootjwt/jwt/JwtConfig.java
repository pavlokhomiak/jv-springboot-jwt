package com.example.jvspringbootjwt.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

@Data
@Component
@ConfigurationProperties("spring.jwt")
public class JwtConfig {
    private String key;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
