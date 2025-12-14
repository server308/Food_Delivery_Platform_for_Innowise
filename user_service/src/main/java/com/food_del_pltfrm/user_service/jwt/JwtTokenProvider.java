package com.food_del_pltfrm.user_service.jwt;

import com.food_del_pltfrm.user_service.entities.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    public JwtTokenProvider(
            @Value("${jwt.secret.access}") String accessKey,
            @Value("${jwt.secret.refresh}") String refreshKey) {

        // Конвертируем Base64 строки в SecretKey
        this.accessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        this.refreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
    }


    public String generateRefreshToken(String user_id, String email){
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user_id);
        claims.put("type", "refresh");
        return Jwts.builder()
                .setClaims(claims) // Устанавливаем claims
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Устанавливаем время выпуска
                .setExpiration(Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant())) // Устанавливаем срок действия токена (30 дней)
                .signWith(refreshSecretKey)
                .compact();
    }


    public String generateAccessToken(String user_id, String email, List<String> roles){
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user_id);
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims) // Устанавливаем claims
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Устанавливаем время выпуска
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant())) // Устанавливаем срок действия access-токена (5 минут)
                .signWith(accessSecretKey)
                .compact();
    }


    public boolean validateToken(String token, boolean isRefreshToken){
        try {
            SecretKey secret = (isRefreshToken) ? refreshSecretKey : accessSecretKey;
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
        SecretKey secret = (isRefreshToken) ? refreshSecretKey : accessSecretKey;
        Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
        return claims;
    }

    public String getEmailFromToken(String token, boolean isRefreshToken){
        SecretKey secret = (isRefreshToken) ? refreshSecretKey : accessSecretKey;
        return Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
