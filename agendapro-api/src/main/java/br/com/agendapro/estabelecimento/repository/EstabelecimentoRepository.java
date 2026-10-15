package br.com.agendapro.estabelecimento.repository;

import br.com.agendapro.estabelecimento.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, UUID> {
}
