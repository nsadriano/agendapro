package br.com.agendapro.shared;
import br.com.agendapro.estabelecimento.Estabelecimento;
import jakarta.persistence.*;
@MappedSuperclass
public abstract class TenantEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estabelecimento_id", nullable = false, updatable = false)
    private Estabelecimento estabelecimento;
    public Estabelecimento getEstabelecimento() { return estabelecimento; }
    public void setEstabelecimento(Estabelecimento estabelecimento) { this.estabelecimento = estabelecimento; }
}
