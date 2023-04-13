package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
