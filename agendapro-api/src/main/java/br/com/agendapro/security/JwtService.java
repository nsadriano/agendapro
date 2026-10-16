package br.com.agendapro.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationSeconds;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-seconds}") long expirationSeconds
    ) {

        byte[] keyBytes = Base64.getDecoder().decode(secret);

        if(keyBytes.length < 32){
            throw new IllegalArgumentException(
              "A chave JWT deve possuir pelo menos 32 bytes"
            );
        }

        if(expirationSeconds <= 0){
            throw new IllegalArgumentException(
                    "A expiração do JWT deve ser positiva"
            );
        }

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationSeconds = expirationSeconds;

    }

    public String generateToken(UUID usuarioId){

        Instant agora = Instant.now();
        Instant expiracao = agora.plusSeconds(expirationSeconds);

        return Jwts.builder()
                .subject(usuarioId.toString())
                .issuedAt(Date.from(agora))
                .expiration(Date.from(expiracao))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public UUID extractUsuarioId(String token){
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return UUID.fromString(claims.getSubject());
    }

    public long getExpirationSeconds(){
        return expirationSeconds;
    }

}
