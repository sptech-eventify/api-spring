package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.buffet.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
}
