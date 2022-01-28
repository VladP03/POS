package com.pos.JWT.jwt;

import com.pos.JWT.model.UserDTO;
import com.pos.JWT.repository.Role;
import com.pos.JWT.service.TokenService;
import com.pos.JWT.service.UserDetailsService;
import com.pos.JWT.service.exception.TokenExpiredException;
import com.pos.JWT.service.exception.TokenInvalidException;
import com.pos.JWT.service.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {

    private final UserDetailsService userDetailsService;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    @Value("${JWT_SECRET}")
    private String signingKey;
    @Value("${JWT_TOKEN_VALIDITY_IN_DAYS}")
    private Long tokenDaysValidity;


    /**
     * @param userDTO The user for which the token will be generated
     * @return The JWT token
     */
    public String generateToken(UserDTO userDTO) {
        final Date tokenAvailability = new Date(System.currentTimeMillis() + tokenDaysValidity * 1000 * 60 * 60 * 24);
        final String jti = UUID.randomUUID().toString();

        return Jwts.builder()
                .setIssuer("localhost:8090")
                .setSubject(userDTO.getUsername())
                .claim("role", userDTO.getRole())
                .setExpiration(tokenAvailability)
                .setId(jti)
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }


    /**
     * @param token The JWT token
     * @return token validity
     */
    public void validateToken(String token) {
        checkIfTokenExpired(token);

        // check if token is on blacklist
        if (TokenService.getBlackList().contains(token)) {
            throw new UnauthorizedException();
        }

        // check user exists in db
        final String username = getClaimFromToken(token, Claims::getSubject);
        userDetailsService.checkIfUserExists(username);
    }


    /**
     * @param token The JWT token
     * @return The subject's role
     */
    public Role getSubjectRole(String token) {
        final String username = getClaimFromToken(token, Claims::getSubject);

        return userDetailsService.getRole(username);
    }


    /**
     * @param token The JWT token
     * @return The subject
     */
    public String getSubject(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    private void checkIfTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);

        if (expiration.before(new Date())) {
            throw new TokenExpiredException();
        }
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token)
                    .getBody();

            return claimsResolver.apply(claims);
        } catch (SignatureException exception) {
            throw new TokenInvalidException();
        } catch (ExpiredJwtException exception) {
            throw new TokenExpiredException();
        }
    }
}
