package eventify.api_spring.repository;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImagemRepository extends JpaRepository<Imagem, Integer> {

    @Query("select i.caminho from Imagem i where i.buffet = :buffet")
    public String findByBuffet(Buffet buffet);

}
