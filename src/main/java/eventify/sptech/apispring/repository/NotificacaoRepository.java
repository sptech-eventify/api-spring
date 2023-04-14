package eventify.sptech.apispring.repository;

import eventify.sptech.apispring.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
}
