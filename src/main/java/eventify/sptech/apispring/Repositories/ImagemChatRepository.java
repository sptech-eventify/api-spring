package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.ImagemChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagemChatRepository extends JpaRepository<ImagemChat, Integer> {
}
