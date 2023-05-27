package eventify.api_spring.repository;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.dto.BuffetDtoResposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BuffetRepository extends JpaRepository<Buffet, Integer> {

    @Query("SELECT b FROM Buffet b")
    public List<Buffet> findAllBuffet();
    public List<Buffet>findByNomeContainingIgnoreCase(String q);

    /*
    TESTAR DURANTE O PÓS SPRINT PARA MELHOR EFICIÊNCIA DO BACKEND
    @Query("SELECT b FROM Buffet b " +
            "WHERE (:nome IS NULL OR b.nome LIKE %:nome%) " +
            "AND (:tipoEvento IS NULL OR EXISTS (SELECT te FROM b.tiposEventos te WHERE te.descricao IN :tipoEvento)) " +
            "AND (:faixaEtaria IS NULL OR EXISTS (SELECT fe FROM b.faixaEtarias fe WHERE fe.descricao IN :faixaEtaria)) " +
            "AND (:servico IS NULL OR EXISTS (SELECT s FROM b.servicos s WHERE s.descricao IN :servico)) " +
            "AND (:qtdPessoas IS NULL OR b.qtdPessoas >= :qtdPessoas) " +
            "AND (:orcMin IS NULL OR b.precoMedioDiaria >= :orcMin) " +
            "AND (:orcMax IS NULL OR b.precoMedioDiaria <= :orcMax) " +
            "AND (:tamanho IS NULL OR b.tamanho >= :tamanho) " +
            "AND ((:latitude IS NULL OR :longitude IS NULL) OR HAVERSINE(:latitude, :longitude, b.endereco.latitude, b.endereco.longitude) <= 15)")
    public List<Buffet> filtrarBuffet(
            @Param("nome") String nome,
            @Param("tipoEvento") List<String> tipoEvento,
            @Param("faixaEtaria") List<String> faixaEtaria,
            @Param("servico") List<String> servico,
            @Param("qtdPessoas") Integer qtdPessoas,
            @Param("orcMin") Double orcMin,
            @Param("orcMax") Double orcMax,
            @Param("tamanho") Integer tamanho,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude);*/

}
