package eventify.api_spring.repository;

import eventify.api_spring.domain.acesso.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcessoRepository extends JpaRepository<Acesso,Integer> {
}
