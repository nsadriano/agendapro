package br.com.agendapro.auth.dto;

import java.util.UUID;

public record AuthResponse(

       String token,
       String tokenType,
       Long expireIn,
       UUID usuarioId,
       UUID estabelecimentoId

) {
}
