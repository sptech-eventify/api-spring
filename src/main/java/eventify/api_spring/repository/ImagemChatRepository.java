package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eventify.api_spring.domain.chat.ImagemChat;

@Repository
public interface ImagemChatRepository extends JpaRepository<ImagemChat, Integer> {
}
