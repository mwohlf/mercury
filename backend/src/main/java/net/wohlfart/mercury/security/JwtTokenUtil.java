package net.wohlfart.mercury.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import net.wohlfart.mercury.SecurityConstants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Common helper methods to work with JWT
 * see: https://tools.ietf.org/html/rfc7519
 */
@Slf4j
@Component
public class JwtTokenUtil implements Serializable {


    // custom claims
    private static final String CLAIM_KEY_USERID = "uid"; // userid
    private static final String CLAIM_KEY_AUTHORITIES = "auth";

    private String secret = SecurityConstants.SECRET;

    private Long expiration  = SecurityConstants.EXPIRATION_TIME;

    /**
     * Returns user id from given token
     * @param token JSON Web Token
     * @return user id
     */
    public Long getUserIdFromToken(String token) {
        Long id = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            id = Long.valueOf((Integer) claims.get(CLAIM_KEY_USERID));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    /**
     * Returns username from given token
     * @param token JSON Web Token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = String.valueOf(claims.get(Claims.SUBJECT));
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * Returns creation date from given token
     * @param token JSON Web Token
     * @return creation date
     */
    public Date getCreationDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(Claims.ISSUED_AT));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * Returns expiration date from given token
     * @param token JSON Web Token
     * @return expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * Generates JWT using userDetails
     * @param userDetails used to generate JWT
     * @return generated JWT
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        UserDetailsImpl jwtUser = (UserDetailsImpl) userDetails;
        claims.put(CLAIM_KEY_USERID, jwtUser.getId());
        claims.put(Claims.SUBJECT, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUTHORITIES, userDetails.getAuthorities());
        claims.put(Claims.ISSUED_AT, new Date());
        return generateToken(claims);
    }

    /**
     * Refreshes JWT
     * @param token old JWT
     * @return refreshed JWT
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(Claims.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * Checks token validity
     * @param token to check
     * @return true if token valid else false
     */
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        if (username == null) {
            return false;
        }
        Claims claims = getClaimsFromToken(token);
        if (new Date().after(claims.getExpiration())) {
            return false;
        }
        return true;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
