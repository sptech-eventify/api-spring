package eventify.api_spring.repository;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.evento.Evento;
import eventify.api_spring.dto.evento.EventoDto;
import eventify.api_spring.dto.evento.EventoOrcamentoDto;
import eventify.api_spring.dto.orcamento.OrcamentoDto;
import eventify.api_spring.dto.orcamento.OrcamentoPropDto;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
        @Query("SELECT ROUND(AVG(e.nota), 2) FROM Evento e WHERE e.buffet = :buffet")
        Double findAvaliacaoByBuffet(@Param("buffet") Buffet buffet);

        @Query("SELECT e FROM Evento e WHERE e.buffet = :buffet")
        List<Evento> findByBuffet(Buffet buffet);

        @Query("SELECT e FROM Evento e WHERE e.buffet.nome = :nome")
        Optional<Evento> findByBuffet(String nome);

        @Query("SELECT e.data FROM Evento e WHERE e.buffet = :buffet")
        List<LocalDate> findAllDataByBuffet(@Param("buffet") Buffet buffet);

        @Query("SELECT new eventify.api_spring.dto.evento.EventoOrcamentoDto(u.nome, e.data, e.status) FROM Evento e JOIN Usuario u ON e.contratante = u.id WHERE e.buffet = :buffet")
        List<EventoOrcamentoDto> findAllOrcamentosByBuffetPublico(@Param("buffet") Buffet buffet);

        @Query("SELECT new eventify.api_spring.dto.evento.EventoDto(e.id, b.nome, e.data, e.preco, e.nota, t.descricao, CONCAT(i.caminho, '/', i.nome, '.', i.tipo), '6') FROM Buffet b "
                        +
                        "JOIN b.tiposEventos t " +
                        "JOIN Evento e ON e.buffet = b " +
                        "JOIN Imagem i on i.buffet = b " +
                        "WHERE e.status = 6 " +
                        "AND e.contratante.id = :id " +
                        "GROUP BY e")
        List<EventoDto> findAllEventosInfo(@Param("id") Integer id);

        @Query("SELECT new eventify.api_spring.dto.evento.EventoDto(e.id, b.nome, e.data, e.preco, e.nota, t.descricao, CONCAT(i.caminho, '/', i.nome, '.', i.tipo), e.status) FROM Buffet b "
                        +
                        "JOIN b.tiposEventos t " +
                        "JOIN Evento e ON e.buffet = b " +
                        "JOIN Imagem i on i.buffet = b " +
                        "WHERE e.status != '6' " +
                        "AND e.contratante.id = :id " +
                        "GROUP BY b.nome," +
                        "b.id")
        List<EventoDto> findAllOrcamentos(@Param("id") Integer id);

        @Query("SELECT new eventify.api_spring.dto.orcamento.OrcamentoDto(b.nome, u.nome AS nomeContratante, u.cpf As cpfContratante, u.email AS emailContratante, CONCAT(en.logradouro, ',', en.numero, ',', en.cidade, ' - ', en.uf), e.data, "
                        +
                        "GROUP_CONCAT(DISTINCT ts.servico.descricao), GROUP_CONCAT(DISTINCT fe.descricao), GROUP_CONCAT(DISTINCT te.descricao), e.preco) "
                        +
                        "FROM Buffet b " +
                        "JOIN Evento e ON e.buffet.id = b.id " +
                        "JOIN Usuario u ON e.contratante.id = u.id " +
                        "JOIN b.endereco en " +
                        "JOIN b.faixaEtarias fe " +
                        "JOIN b.tiposEventos te " +
                        "JOIN b.servicos ts " +
                        "WHERE e.id = :idEvento " +
                        "GROUP BY u.nome, b.nome, b.id, e.data, e.preco")
        OrcamentoDto findOrcamentoById(@Param("idEvento") Integer idEvento);

        @Query("SELECT e.status FROM Evento e WHERE e.id = :idEvento")
        Integer findStatusByEvento(@Param("idEvento") Integer idEvento);

        @Query("SELECT new eventify.api_spring.dto.orcamento.OrcamentoPropDto(e.id, u.nome, e.data, e.preco, e.status)"
                        +
                        "FROM Evento e " +
                        "JOIN Usuario u on e.contratante = u " +
                        "JOIN Buffet b on e.buffet = b " +
                        "WHERE b.id = :idBuffet AND " +
                        "e.status != '6' " +
                        "ORDER BY e.status")
        List<OrcamentoPropDto> findAllOrcamentosByBuffet(@Param("idBuffet") Integer idBuffet);

        @Procedure(procedureName = "sp_proximo_evento")
        @Transactional
        List<Object[]> spProximoEvento(@Param("idBuffet") Integer idBuffet);

        Integer countAllByBuffetId(@Param("idBuffet") Integer idBuffet);

        @Query("SELECT e.nota FROM Evento e WHERE e.buffet = :buffet AND e.status = 6")
        List<Double> findNotaByBuffet(@Param("buffet") Buffet buffet);

        @Query("SELECT e.nota FROM Evento e WHERE e.buffet = :buffet AND e.data BETWEEN :dataInicial AND :dataFinal")
        List<Double> findNotaMesAtualByBuffet(Buffet buffet, LocalDate dataInicial, LocalDate dataFinal);

        @Query("SELECT e.nota FROM Evento e WHERE e.buffet = :buffet AND e.data BETWEEN :dataInicial AND :dataFinal")
        List<Evento> findNotaUltimoMesByBuffet(Buffet buffet, LocalDate dataInicial, LocalDate dataFinal);
}