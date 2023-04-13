package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Mensagem;
import eventify.sptech.apispring.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {

    @Query("select m from Mensagem m where m.usuario = :remetente and m.mandadoPor = false")
    public List<Mensagem> findAllByRemetente(Usuario remetente);

}
