package eventify.api_spring.repository;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.buffet.Imagem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagemRepository extends JpaRepository<Imagem, Integer> {

    @Query("select i from Imagem i where i.buffet = :buffet")
    public List<Imagem> findByBuffet(Buffet buffet);

}
