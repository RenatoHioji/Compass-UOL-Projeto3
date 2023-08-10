package com.uol.pb.challenge3.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private String jwtExpiration;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Instant currentInstant = Instant.now();
        Instant expireInstant = currentInstant.plus(Duration.ofSeconds(Long.parseLong(jwtExpiration)));
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(currentInstant))
                .setExpiration(Date.from(expireInstant))
                .signWith(key())
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }
        catch (MalformedJwtException ex){
            throw new ResourceNotFoundException("Invalid JWT Token");
        }
        catch (ExpiredJwtException ex){
            throw new ResourceNotFoundException("Expired JWT Token");
        }
        catch (UnsupportedJwtException ex){
            throw new ResourceNotFoundException("Unsopported JWT Token");
        }
        catch (IllegalArgumentException ex){
            throw new ResourceNotFoundException("JWT claims string is empty");
        }
    }
}
