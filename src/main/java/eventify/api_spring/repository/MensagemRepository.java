package eventify.api_spring.repository;


import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.chat.Mensagem;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.chat.ChatListaDto;

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

    @Query("select new eventify.api_spring.dto.ChatListaDto(m.buffet.id, m.buffet.nome, m.mensagem, m.data) from Mensagem m where m.usuario.id = :idUsuario group by m.buffet.nome order by m.id desc")
    public List<ChatListaDto> findAllChatByUsuario(int idUsuario);

    @Query("select distinct new eventify.api_spring.dto.ChatListaDto(m.buffet.id, m.buffet.nome, m.mensagem, m.data) from Mensagem m group by m.buffet order by m.id")
    public List<ChatListaDto> findAllChatListaDto();

    Integer countMensagemByBuffetAndUsuario(Buffet buffet, Usuario usuario);
}
