package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.chat.ImagemChat;

public interface ImagemChatRepository extends JpaRepository<ImagemChat, Integer> {
}
