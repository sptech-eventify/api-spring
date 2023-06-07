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
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuffetRepository extends JpaRepository<Buffet, Integer> {
    @Query("SELECT b FROM Buffet b")
    public List<Buffet> findAllBuffet();
    public List<Buffet>findByNomeContainingIgnoreCase(String q);

    @Validated
    @Query("SELECT new eventify.api_spring.dto.BuffetPublicDto(b.nome, " +
            "u.nome, " +
            "b.descricao, " +
            "GROUP_CONCAT(DISTINCT CONCAT(i.caminho, '/', i.nome, '.', i.tipo)), " +
            "b.precoMedioDiaria, " +
            "round(avg(e.nota),2), " +
            "GROUP_CONCAT(DISTINCT ts.descricao)," +
            "GROUP_CONCAT(DISTINCT fe.descricao)," +
            "GROUP_CONCAT(DISTINCT te.descricao),  " +
            "end.latitude, " +
            "end.longitude," +
            "end.logradouro," +
            "end.numero," +
            "end.bairro," +
            "end.cidade," +
            "end.uf," +
            "end.cep) " +
            "FROM Buffet b " +
            "JOIN Usuario u on b.usuario = u " +
            "JOIN Imagem i on i.buffet = b " +
            "JOIN Evento e on e.buffet = b " +
            "JOIN b.servicos as ts " +
            "JOIN b.faixaEtarias as fe " +
            "JOIN b.tiposEventos as te " +
            "JOIN Endereco end on b.endereco = end " +
            "WHERE b.id = :idBuffet " +
            "GROUP BY b")
    BuffetPublicDto findBuffetPublicDtoById(int idBuffet);

    @Query("SELECT new eventify.api_spring.dto.BuffetInfoDto(b.id," +
            "GROUP_CONCAT(DISTINCT te.descricao)," +
            "b.nome," +
            "b.precoMedioDiaria," +
            "b.tamanho," +
            "b.qtdPessoas," +
            "ROUND(AVG(e.nota),1) as notaMedia," +
            "GROUP_CONCAT(DISTINCT CONCAT(i.caminho, i.nome, '.', i.tipo))) " +
            "FROM Buffet b " +
            "JOIN Evento e on e.buffet = b " +
            "JOIN b.tiposEventos as te " +
            "JOIN Imagem i on i.buffet = b " +
            "GROUP BY b.nome")
    List<BuffetInfoDto> findAllBuffetInfo();

    @Query("SELECT new eventify.api_spring.dto.BuffetInfoDto(b.id," +
            "GROUP_CONCAT(DISTINCT te.descricao)," +
            "b.nome," +
            "b.precoMedioDiaria," +
            "b.tamanho," +
            "b.qtdPessoas," +
            "ROUND(AVG(e.nota),1) as notaMedia," +
            "GROUP_CONCAT(DISTINCT CONCAT(i.caminho, i.nome, '.', i.tipo))) " +
            "FROM Buffet b " +
            "JOIN Evento e on e.buffet = b " +
            "JOIN b.tiposEventos as te " +
            "JOIN Imagem i on i.buffet = b " +
            "JOIN Usuario u on b.usuario = u " +
            "WHERE u.id = :idUser " +
            "GROUP BY b.nome")
    List<BuffetInfoDto> findAllBuffetProprietario(int idUser);

    public Buffet findBuffetById(int idBuffet);

    List<Buffet> findAllByNome(String nome);

    List<Buffet> findAllByNomeContaining(String nome);

    List<Buffet> findAllByUsuario(Usuario usuario);
}