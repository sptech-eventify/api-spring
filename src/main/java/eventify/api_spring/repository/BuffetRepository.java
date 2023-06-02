package eventify.api_spring.repository;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.BuffetDtoResposta;
import eventify.api_spring.dto.BuffetInfoDto;
import eventify.api_spring.dto.BuffetPublicDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuffetRepository extends JpaRepository<Buffet, Integer> {
    @Query("SELECT b FROM Buffet b")
    public List<Buffet> findAllBuffet();
    public List<Buffet>findByNomeContainingIgnoreCase(String q);

    @Query("SELECT new eventify.api_spring.dto.BuffetPublicDto(b.nome, " +
            "b.descricao, " +
            "GROUP_CONCACT(DISTINCT CONCAT(i.caminho, '/', i.nome, '.', i.tipo)), " +
            "b.precoMedioDiaria, " +
            "round(avg(e.nota),2)) " +
            "FROM Buffet b " +
            "JOIN Imagem i on i.buffet = b " +
            "JOIN Evento e on e.buffet = b " +
            "WHERE b.id = :idBuffet " +
            "GROUP BY b")
    BuffetPublicDto findBuffetPublicDtoById(int idBuffet);

    public Buffet findBuffetById(int idBuffet);

    List<Buffet> findAllByNome(String nome);

    List<Buffet> findAllByNomeContaining(String nome);

    List<Buffet> findAllByUsuario(Usuario usuario);
}