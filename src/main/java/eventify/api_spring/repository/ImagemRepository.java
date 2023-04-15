package eventify.api_spring.repository;

import eventify.api_spring.domain.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemRepository extends JpaRepository<Imagem, Integer> {
}
