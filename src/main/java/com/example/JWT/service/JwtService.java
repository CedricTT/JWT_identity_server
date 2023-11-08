package com.example.JWT.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtService {

    //todo: create a vault to store the secret key
    public static final String SECRET = "6c6b20f5b0fe039a441042b7d11758b02cc727879abde3738729db59bcdda61f";

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Objects> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails);
    }

    private String buildToken(Map<String, Objects> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean verifyToken(String token, String username) {
        Jws<Claims> jws = extractAllClaims(token);
        return (username.equals(jws.getPayload().getSubject()) && !isTokenExpired(jws));
    }

    private Jws<Claims> extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token);
    }

    private boolean isTokenExpired(Jws<Claims> token) {
        return token.getPayload().getExpiration().before(new Date());
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
