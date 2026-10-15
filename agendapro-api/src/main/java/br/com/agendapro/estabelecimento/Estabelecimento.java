package br.com.agendapro.estabelecimento;

import jakarta.persistence.*;
import br.com.agendapro.shared.BaseEntity;
import java.time.Instant;

@Entity
@Table(name = "estabelecimento")
public class Estabelecimento extends BaseEntity {
    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "criado_em")
    private Instant criadoEm;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Instant getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Instant criadoEm) { this.criadoEm = criadoEm; }

}