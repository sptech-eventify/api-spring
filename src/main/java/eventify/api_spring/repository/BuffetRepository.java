package eventify.api_spring.repository;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.usuario.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuffetRepository extends JpaRepository<Buffet, Integer> {
    @Query("SELECT b FROM Buffet b")
    List<Buffet> findAllBuffet();

    List<Buffet> findByIsVisivelTrue();

    Buffet findByIsVisivelTrueAndId( Integer idBuffet);

    Double findAvaliacaoByIsVisivelTrueAndId(Integer idBuffet);

    List<Buffet>findByNomeContainingIgnoreCase(String q);

    List<Buffet> findBuffetByUsuario(Usuario usuario);

    Buffet findBuffetById(Integer idBuffet);

    List<Buffet> findAllByNome(String nome);

    List<Buffet> findAllByNomeContaining(String nome);

    List<Buffet> findAllByUsuario(Usuario usuario);
}