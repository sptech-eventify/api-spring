package eventify.api_spring.repository;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Evento;
import eventify.api_spring.dto.EventoDto;
import eventify.api_spring.dto.OrcamentoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    @Query("select e from Evento e where e.buffet = :buffet")
    public List<Evento> findByBuffet(Buffet buffet);

    @Query("select e from Evento e where e.buffet.nome = :nome")
    Optional<Evento> findByBuffet(String nome);

    @Query("select e.data from Evento e where e.buffet = :buffet")
    public List<LocalDate> findAllDataByBuffet(Buffet buffet);

    @Query("select round(avg(e.nota),2) from Evento e where e.buffet = :buffet")
    public Double findAvaliacaoByBuffet(Buffet buffet);

    @Query("SELECT new eventify.api_spring.dto.EventoDto(e.id, b.nome, e.data, e.preco, e.nota, t.descricao, CONCAT(i.caminho, '/', i.nome, '.', i.tipo), '6') FROM Buffet b " +
            "JOIN b.tiposEventos t " +
            "JOIN Evento e ON e.buffet = b " +
            "JOIN Imagem i on i.buffet = b " +
            "WHERE e.status = 6 " +
            "AND e.contratante.id = :id " +
            "GROUP BY e")
    List<EventoDto> findAllEventosInfo(int id);

    @Query("SELECT new eventify.api_spring.dto.EventoDto(e.id, b.nome, e.data, e.preco, e.nota, t.descricao, CONCAT(i.caminho, '/', i.nome, '.', i.tipo), e.status) FROM Buffet b " +
            "JOIN b.tiposEventos t " +
            "JOIN Evento e ON e.buffet = b " +
            "JOIN Imagem i on i.buffet = b " +
            "WHERE e.status != 6 " +
            "AND e.contratante.id = :id " +
            "GROUP BY b.nome")
    List<EventoDto> findAllOrcamentos(int id);

    @Query("SELECT new eventify.api_spring.dto.OrcamentoDto(" +
            "b.nome," +
            "u.nome as nomeContratante," +
            "concat(en.logradouro, ', ', en.numero, ', ', en.cidade, ' - ',en.uf)," +
            "e.data," +
            "GROUP_CONCAT(DISTINCT ts.descricao)," +
            "GROUP_CONCAT(DISTINCT fe.descricao)," +
            "GROUP_CONCAT(DISTINCT te.descricao)," +
            "e.preco) " +
            "FROM Buffet b " +
            "JOIN Evento e on e.buffet = b " +
            "JOIN Usuario u on b.usuario = u " +
            "JOIN Endereco en on b.endereco = en " +
            "JOIN b.servicos ts " +
            "JOIN b.faixaEtarias fe " +
            "JOIN b.tiposEventos te " +
            "WHERE u.id = :id " +
            "AND e.id = :idEvento " +
            "GROUP BY b.nome ")
    List<OrcamentoDto> findOrcamentoById(int id, int idEvento);


}

