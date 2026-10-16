package br.com.agendapro.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp(){
        byte[] chave = new byte[32];

        for(int i = 0; i < chave.length; i++){
            chave[i] = (byte)(i + 1);
        }

        String secret = Base64.getEncoder()
                .encodeToString(chave);

        jwtService = new JwtService(secret, 3600);

    }

    @Test
    void deveGerarTokenERecuperarUsuarioId(){

        UUID usuarioId = UUID.randomUUID();

        String token = jwtService.generateToken(usuarioId);

        assertNotNull(token);
        assertFalse(token.isBlank());

        UUID usuarioIdExtraido = jwtService.extractUsuarioId(token);

        assertEquals(usuarioId, usuarioIdExtraido);

    }

    @Test
    void deveRejeitarTokenAdulterado(){
        UUID usuarioId = UUID.randomUUID();

        String token = jwtService.generateToken(usuarioId);

        String[] partes = token.split("\\.");

        partes[1] = partes[1].charAt(0) == 'A'
                ? "B" + partes[1].substring(1)
                : "A" + partes[1].substring(1);

        String tokenAdulterado = String.join(".", partes);

        assertThrows(
                JwtException.class,
                () -> jwtService.extractUsuarioId(tokenAdulterado)
        );
    }

    @Test
    void deveRejeitarTokenExpirado() throws InterruptedException {

        byte[] chave = new byte[32];

        for(int i = 0; i < chave.length; i++){
            chave[i] = (byte)(i + 1);
        }

        String secret = Base64.getEncoder()
                .encodeToString(chave);

        jwtService = new JwtService(secret, 1);

        String token = jwtService.generateToken(UUID.randomUUID());

        Thread.sleep(1500);

        assertThrows(
                ExpiredJwtException.class,
                () -> jwtService.extractUsuarioId(token)
        );
    }

}
