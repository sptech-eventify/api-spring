package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Contratante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratanteRepository extends JpaRepository<Contratante, Integer> {
}
