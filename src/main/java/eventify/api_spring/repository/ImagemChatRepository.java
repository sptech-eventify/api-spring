package eventify.api_spring.repository;

import eventify.api_spring.domain.ImagemChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemChatRepository extends JpaRepository<ImagemChat, Integer> {
}
