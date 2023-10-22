package eventify.api_spring.repository;

import eventify.api_spring.domain.buffet.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    @Query("SELECT t FROM Transacao t WHERE t.buffet.id = :id")
    List<Transacao> findAllByBuffetId(Integer id);
}
