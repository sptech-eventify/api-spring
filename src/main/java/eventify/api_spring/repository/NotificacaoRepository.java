package eventify.api_spring.repository;

import eventify.api_spring.domain.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;



public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
}
