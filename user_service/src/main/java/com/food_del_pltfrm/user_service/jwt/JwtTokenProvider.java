package com.food_del_pltfrm.user_service.jwt;

import com.food_del_pltfrm.user_service.entities.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

    private String accessKey;
    private String secretKey;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret.access}") String accessKey, @Value("${jwt.secret.refresh}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    public String generateRefreshToken(String fullName){
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return Jwts.builder()
                .setClaims(claims) // Устанавливаем claims
                .setSubject(fullName) // Устанавливаем subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Устанавливаем время выпуска
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24* 30)) // Устанавливаем срок действия токена (30 дней)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public String generateAccessToken(String fullName, List<Role> roles){
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.toString());
        return Jwts.builder()
                .setClaims(claims) // Устанавливаем claims
                .setSubject(fullName) // Устанавливаем subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Устанавливаем время выпуска
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant())) // Устанавливаем срок действия access-токена (5 минут)
                .signWith(SignatureAlgorithm.HS256, accessKey)
                .compact();
    }


    public boolean validateToken(String token, boolean isRefreshToken){
        try {
            String secret = (isRefreshToken) ? secretKey : accessKey;
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAllClaimsFromToken(String token, boolean isRefreshToken){
        String secret = (isRefreshToken) ? secretKey : accessKey;
        Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
        return claims;
    }

    public String getFullNameFromToken(String token, boolean isRefreshToken){
        String secret = (isRefreshToken) ? secretKey : accessKey;
        return Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
