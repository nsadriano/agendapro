package br.com.agendapro.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 120, message = "")
        String nome,
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Informe um e-mail válido")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, max = 72,
        message = "A senha deve ter entre 8 e 72 caracteres")
        String senha,
        @NotBlank(message = "O nome do estabelecimento é obrigatório")
        @Size(min = 3, max = 150,
        message = "O nome do estabelecimento deve ter entre 3 e 150 caracteres")
        String estabelecimento

) {
}
