package com.ramen.ordering.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ramen.ordering.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours

    private final String secret;

    public JwtTokenUtil(JwtProperties jwtProperties) {
        this.secret = jwtProperties.getSecret();
    }

    // Retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, com.auth0.jwt.interfaces.DecodedJWT::getSubject);
    }

    // Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, decodedJWT -> new Date(decodedJWT.getExpiresAt().getTime()));
    }

    public <T> T getClaimFromToken(String token, Function<com.auth0.jwt.interfaces.DecodedJWT, T> claimsResolver) {
        final com.auth0.jwt.interfaces.DecodedJWT decodedJWT = getAllClaimsFromToken(token);
        return claimsResolver.apply(decodedJWT);
    }

    // For retrieving any information from token we will need the secret key
    private com.auth0.jwt.interfaces.DecodedJWT getAllClaimsFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).build().verify(token);
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        try {
            com.auth0.jwt.interfaces.DecodedJWT decodedJWT = getAllClaimsFromToken(token);
            Date expiresAt = decodedJWT.getExpiresAt();
            return expiresAt.before(new Date());
        } catch (Exception e) {
            return true; // If we can't decode, treat as expired
        }
    }

    // Generate token for user
    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole().name());
        return createToken(claims, user.getId().toString());
    }

    // Create the actual token
    private String createToken(Map<String, Object> claims, String subject) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(JWT_TOKEN_VALIDITY)))
                .withPayload(claims)
                .sign(Algorithm.HMAC256(secret));
    }

    // Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }
}