package com.pos.JWT.jwt;

import com.pos.JWT.model.UserDTO;
import com.pos.JWT.repository.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String signingKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    @Value("${server.address}:${server.port}")
    private String server;


    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(server)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }



    //generate token for user
    public String generateToken(UserDTO userDTO) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("role", userDTO.getRole());

        return createToken(claims, userDTO.getUsername());
    }



    //validate token
    public Boolean isValidToken(String token) {
        // 1. perioada de valabilitate
        if (isTokenExpired(token)) {
            return false;
        }

        // 2. semnatura digitala
        // daca nu ar fi buna semnatura digitala, nu s-ar fi putet extrage perioada de valabilitate

        return true;
    }



    //for retrieving any information from token we will need the secret key
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException exception) {
            throw new RuntimeException("Token expired");
        } catch (SignatureException exception) {
            throw new RuntimeException("Token invalid (signatureException)");
        }
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }



    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve role from jwt token
    public Role getRoleFromToken(String token) {
        String role = extractAllClaims(token).get("role").toString();

        switch (role.toUpperCase(Locale.ROOT)) {
            case "USER":
                return Role.USER;
            case "ADMIN":
                return Role.ADMIN;
            default:
                throw new RuntimeException("PROBLEM AT GETTING USER ROLE");
        }
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }



    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }
}
