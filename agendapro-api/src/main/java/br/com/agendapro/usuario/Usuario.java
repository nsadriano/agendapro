package br.com.agendapro.usuario;

import jakarta.persistence.*;
import br.com.agendapro.shared.BaseEntity;
import java.time.Instant;

@Entity
@Table(name = "usuario")
public class Usuario extends BaseEntity {
    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha_hash")
    private String senhaHash;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "criado_em")
    private Instant criadoEm;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Instant getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Instant criadoEm) { this.criadoEm = criadoEm; }

}