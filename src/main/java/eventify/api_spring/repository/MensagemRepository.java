package eventify.api_spring.repository;


import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Mensagem;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.ChatListaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {

    @Query("select m from Mensagem m where m.usuario = :usuario and m.mandadoPor = false")
    public List<Mensagem> findAllByUsuario(Usuario usuario);

    @Query("select m from Mensagem m where m.buffet = :buffet and m.mandadoPor = true")
    public List<Mensagem> findAllByBuffet(Buffet buffet);

    @Query("select m from Mensagem m where m.usuario = :usuario and m.buffet = :buffet order by m.id desc")
    public List<Mensagem> findAllByUsuarioBuffet(Usuario usuario, Buffet buffet);

    @Query("select new eventify.api_spring.dto.ChatListaDto(m.buffet.id, m.buffet.nome, m.mensagem, m.data) from Mensagem m where m.usuario.id = :idUsuario and m.mandadoPor = true order by m.id desc limit 1")
    public List<ChatListaDto> findAllChatByUsuario(int idUsuario);

    Integer countMensagemByBuffetAndUsuario(Buffet buffet, Usuario usuario);
}
