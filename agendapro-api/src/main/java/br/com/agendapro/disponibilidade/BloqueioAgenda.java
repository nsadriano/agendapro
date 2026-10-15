package br.com.agendapro.disponibilidade;

import jakarta.persistence.*;
import br.com.agendapro.shared.TenantEntity;
import br.com.agendapro.estabelecimento.MembroEstabelecimento;
import java.time.Instant;

@Entity
@Table(name = "bloqueio_agenda")
public class BloqueioAgenda extends TenantEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private MembroEstabelecimento profissional;

    @Column(name = "inicio")
    private Instant inicio;

    @Column(name = "fim")
    private Instant fim;

    @Column(name = "motivo")
    private String motivo;

    public MembroEstabelecimento getProfissional() { return profissional; }
    public void setProfissional(MembroEstabelecimento profissional) { this.profissional = profissional; }

    public Instant getInicio() { return inicio; }
    public void setInicio(Instant inicio) { this.inicio = inicio; }

    public Instant getFim() { return fim; }
    public void setFim(Instant fim) { this.fim = fim; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

}