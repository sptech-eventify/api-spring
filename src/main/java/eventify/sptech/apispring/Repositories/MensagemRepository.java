package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
}
