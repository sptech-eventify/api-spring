package eventify.api_spring.repository;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.evento.Evento;
import eventify.api_spring.dto.evento.EventoDto;
import eventify.api_spring.dto.orcamento.OrcamentoDto;
import eventify.api_spring.dto.orcamento.OrcamentoPropDto;

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
            "WHERE e.status != '6' " +
            "AND e.contratante.id = :id " +
            "GROUP BY b.nome," +
            "b.id")
    List<EventoDto> findAllOrcamentos(int id);

    @Query("SELECT new eventify.api_spring.dto.OrcamentoDto(b.nome, u.nome AS nomeContratante, CONCAT(en.logradouro, ',', en.numero, ',', en.cidade, ' - ', en.uf), e.data, GROUP_CONCAT(DISTINCT ts.descricao), GROUP_CONCAT(DISTINCT fe.descricao), GROUP_CONCAT(DISTINCT te.descricao), e.preco) " +
            "FROM Buffet b " +
            "JOIN Evento e ON e.buffet = b\n" +
            "JOIN Usuario u ON e.contratante = u\n" +
            "JOIN b.endereco en\n" +
            "JOIN b.servicos ts\n" +
            "JOIN b.faixaEtarias fe\n" +
            "JOIN b.tiposEventos te\n" +
            "WHERE e.id = :idEvento\n" +
            "GROUP BY u.nome," +
            "b.nome," +
            "b.id," +
            "e.data," +
            "e.preco")
    OrcamentoDto findOrcamentoById(int idEvento);

    @Query("SELECT e.status FROM Evento e WHERE e.id = :idEvento")
    Integer findStatusByEvento(int idEvento);

    @Query("SELECT new eventify.api_spring.dto.OrcamentoPropDto(e.id, u.nome, e.data, e.preco, e.status)" +
            "FROM Evento e " +
            "JOIN Usuario u on e.contratante = u " +
            "JOIN Buffet b on e.buffet = b " +
            "WHERE b.id = :idBuffet AND " +
            "e.status != '6' " +
            "ORDER BY e.status")
    List<OrcamentoPropDto> findAllOrcamentosByBuffet(int idBuffet);

}

