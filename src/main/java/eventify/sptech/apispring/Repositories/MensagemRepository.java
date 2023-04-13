package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Buffet;
import eventify.sptech.apispring.Entities.Mensagem;
import eventify.sptech.apispring.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {

    @Query("select m from Mensagem m where m.usuario = :usuario and m.mandadoPor = false")
    public List<Mensagem> findAllByUsuario(Usuario usuario);

    @Query("select m from Mensagem m where m.buffet = :buffet and m.mandadoPor = true")
    public List<Mensagem> findAllByBuffet(Buffet buffet);

    @Query("select m from Mensagem m where m.usuario = :usuario and m.buffet = :buffet")
    public List<Mensagem> findAllByUsuarioBuffet(Usuario usuario, Buffet buffet);

}
