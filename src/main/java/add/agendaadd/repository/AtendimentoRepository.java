package add.agendaadd.repository;

import add.agendaadd.entity.Atendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    @Query("""
    SELECT a FROM Atendimento a
    WHERE (:clienteId IS NULL OR a.cliente.id = :clienteId)
      AND (:inicio IS NULL OR a.dataCriacao >= :inicio)
      AND (:fim IS NULL OR a.dataCriacao <= :fim)
""")
    Page<Atendimento> buscarComFiltro(
            @Param("clienteId") Long clienteId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable
    );

}
