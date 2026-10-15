package br.com.agendapro.estabelecimento.repository;

import br.com.agendapro.estabelecimento.MembroEstabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MembroEstabelecimentoRepository extends JpaRepository<MembroEstabelecimento, UUID> {

    List<MembroEstabelecimento> findByUsuarioIdAndAtivoTrue(
            UUID usuarioId
    );

    Optional<MembroEstabelecimento> findByUsuarioIdAndEstabelecimentoIdAndAtivoTrue(
            UUID usuarioId,
            UUID estabelecimentoId
    );

    boolean existsByUsuarioIdAndEstabelecimentoIdAndAtivoTrue(
            UUID usuarioId,
            UUID estabelecimentoId
    );

}
