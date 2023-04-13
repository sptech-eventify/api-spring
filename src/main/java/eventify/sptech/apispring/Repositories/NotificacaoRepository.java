package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
}
