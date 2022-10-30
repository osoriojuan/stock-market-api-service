package cl.juan.stockmarketapiservice.infrastructure.configuration.security;

import cl.juan.stockmarketapiservice.infrastructure.adapter.outbound.persistense.security.services.UserDetailsImpl;
import cl.juan.stockmarketapiservice.infrastructure.configuration.properties.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final SecurityProperties securityProperties;

    public String generateJwtToken(Authentication authentication) {
        log.info("Generating jwt token...");
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        var zonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        Date now = Date.from(zonedDateTime.toInstant());
        Date expiration = Date.from(zonedDateTime.toInstant()
                .plus(securityProperties.getJwtTokenExpirationInMillis(), ChronoUnit.MILLIS));

        return Jwts.builder()
                .setIssuer("Stormpath")
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Key getSigningKey() {
        log.info("Getting signing key...");
        byte[] keyBytes = securityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isValidJwtToken(String authToken) {
        log.info("Validating JwtToken...");
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            log.info("Jwt token validated successfully!");
            return true;
        } catch (SignatureException e) {
            log.error("Security JWT Exception: [{}]", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: [{}]", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: [{}]", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: [{}]", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: [{}]", e.getMessage());
        } catch (Exception e) {
            log.info("An unexpected error occurred while validating the jwt-token [{}]", e.getMessage());
        }
        log.info("The jwt token validation has not been successful");
        return false;
    }
}