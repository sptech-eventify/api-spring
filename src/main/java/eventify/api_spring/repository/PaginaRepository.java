package eventify.api_spring.repository;

import eventify.api_spring.domain.acesso.Pagina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaginaRepository extends JpaRepository<Pagina,Integer> {
}
