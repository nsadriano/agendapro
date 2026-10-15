package br.com.agendapro.agendamento;

import jakarta.persistence.*;
import br.com.agendapro.shared.TenantEntity;
import br.com.agendapro.cliente.Cliente;
import br.com.agendapro.estabelecimento.MembroEstabelecimento;
import br.com.agendapro.servico.Servico;
import br.com.agendapro.shared.StatusAgendamento;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "agendamento")
public class Agendamento extends TenantEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private MembroEstabelecimento profissional;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(name = "inicio")
    private Instant inicio;

    @Column(name = "fim")
    private Instant fim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAgendamento status;

    @Column(name = "preco_previsto")
    private BigDecimal precoPrevisto;

    @Column(name = "valor_cobrado")
    private BigDecimal valorCobrado;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "criado_em")
    private Instant criadoEm;

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public MembroEstabelecimento getProfissional() { return profissional; }
    public void setProfissional(MembroEstabelecimento profissional) { this.profissional = profissional; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public Instant getInicio() { return inicio; }
    public void setInicio(Instant inicio) { this.inicio = inicio; }

    public Instant getFim() { return fim; }
    public void setFim(Instant fim) { this.fim = fim; }

    public StatusAgendamento getStatus() { return status; }
    public void setStatus(StatusAgendamento status) { this.status = status; }

    public BigDecimal getPrecoPrevisto() { return precoPrevisto; }
    public void setPrecoPrevisto(BigDecimal precoPrevisto) { this.precoPrevisto = precoPrevisto; }

    public BigDecimal getValorCobrado() { return valorCobrado; }
    public void setValorCobrado(BigDecimal valorCobrado) { this.valorCobrado = valorCobrado; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Instant getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Instant criadoEm) { this.criadoEm = criadoEm; }

}