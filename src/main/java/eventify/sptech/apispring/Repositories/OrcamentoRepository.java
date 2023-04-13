package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Integer> {
}
