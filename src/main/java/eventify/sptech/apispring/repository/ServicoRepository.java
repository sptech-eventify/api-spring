package eventify.sptech.apispring.repository;

import eventify.sptech.apispring.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {
}
