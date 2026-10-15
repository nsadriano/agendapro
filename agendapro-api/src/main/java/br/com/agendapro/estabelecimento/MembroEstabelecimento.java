package br.com.agendapro.estabelecimento;

import jakarta.persistence.*;
import br.com.agendapro.shared.TenantEntity;
import br.com.agendapro.shared.PapelMembro;
import br.com.agendapro.usuario.Usuario;

@Entity
@Table(name = "membro_estabelecimento")
public class MembroEstabelecimento extends TenantEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel")
    private PapelMembro papel;

    @Column(name = "atende_clientes")
    private Boolean atendeClientes;

    @Column(name = "ativo")
    private Boolean ativo;

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public PapelMembro getPapel() { return papel; }
    public void setPapel(PapelMembro papel) { this.papel = papel; }

    public Boolean getAtendeClientes() { return atendeClientes; }
    public void setAtendeClientes(Boolean atendeClientes) { this.atendeClientes = atendeClientes; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

}