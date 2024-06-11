package com.ibm.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {

    private SecretKey Key;
    private static final long EXPIRATION_TIME = 86400000;

    public JWTUtils() {
        String secretString = "CSfXNjZns3vAU8edBTYFnJGrExnRkurQ7UyeSTeVkTFWjKTcl7ZU9A88mLqzgH34KiC+OcSKtF8ruDWzV3WKQq2dVTxBv6NAERn1sEdaQ++yxZS75z6M+qpiJVaqpJqz9vZTsC4oyR4cc3GHD/7EjuvOyCezT+ENXrDRh3mLW1J29fX2JcBorwaqLJTEVJSkpv90G8ipeI3FTXM786UhsDQYLbYJ9s0v9ugxmQaaxYnxcAR0zS5gL3hPTJi5gKYVT4fAO8QLMHoZJ5u74CqMWtKeh9JA4vQCOsG6MvxU2syxhyemcyEzqqKF3hym53Tnpq8Ktt3kHPAbOLltjZV7Ig==";
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
