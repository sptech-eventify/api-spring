package eventify.api_spring.repository;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.BuffetDtoResposta;
import eventify.api_spring.dto.BuffetInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BuffetRepository extends JpaRepository<Buffet, Integer> {

    @Query("SELECT b FROM Buffet b")
    public List<Buffet> findAllBuffet();
    public List<Buffet>findByNomeContainingIgnoreCase(String q);

    public Buffet findBuffetById(int idBuffet);

    List<Buffet> findAllByNome(String nome);

    List<Buffet> findAllByNomeContaining(String nome);

    List<Buffet> findAllByUsuario(Usuario usuario);

    @Query("SELECT GROUP_CONCAT(DISTINCT te.descricao), b.nome, b.precoMedioDiaria, ROUND(AVG(e.nota), 1), GROUP_CONCAT(DISTINCT CONCAT(i.caminho, '/', i.nome, '.', i.tipo)) " +
            "FROM Buffet b " +
            "JOIN Evento e " +
            "JOIN b.tiposEventos te " +
            "JOIN b.imagens i " +
            "GROUP BY b.nome")
    List<Object[]> findAllBuffetInfo();





}
