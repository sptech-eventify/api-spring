package eventify.api_spring.service;

import eventify.api_spring.domain.Evento;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.EventoCriacaoDto;
import eventify.api_spring.dto.EventoDto;
import eventify.api_spring.dto.OrcamentoDto;
import eventify.api_spring.dto.OrcamentoPropDto;
import eventify.api_spring.mapper.EventoMapper;
import eventify.api_spring.repository.EventoRepository;
import eventify.api_spring.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EntityManager entityManager;


    public List<Evento> exibirTodosEventos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos;
    }

    public Optional<Evento> exibeEvento(String nome) {
        Optional<Evento> evento = eventoRepository.findByBuffet(nome);
        return evento;
    }

    public Boolean criarEvento(EventoCriacaoDto e) {
        eventoRepository.save(EventoMapper.of(e));
        return true;
    }

    public List<Object[]> pegarOrcamentos(int idUser) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUser);
        if (usuarioOpt.isEmpty()) {
            return null;
        }
        Query query = entityManager.createNativeQuery(String.format("select caminho, imagem.nome, tipo,  buffet.nome as buffet_nome, descricao, data, preco, status from evento join buffet on id_buffet = buffet.id join imagem on imagem.id_buffet = buffet.id where id_contratante=%d group by buffet.id;", idUser));
        return query.getResultList();
    }

    public List<EventoDto> listarEventosInfo(int idUser) {
        return eventoRepository.findAllEventosInfo(idUser);
    }

    public List<EventoDto> listarOrcamentos(int idUser) {
        return eventoRepository.findAllOrcamentos(idUser);
    }

    public OrcamentoDto buscarOrcamento(int idEvento) {
        return eventoRepository.findOrcamentoById(idEvento);
    }

    public Boolean mandarOrcamento(int idEvento, double preco) {
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);
        if (eventoOpt.isEmpty()) {
            return false;
        }
        Evento evento = eventoOpt.get();
        evento.setPreco(preco);
        evento.setStatus("3");
        eventoRepository.save(evento);
        return true;
    }

    public Integer verificarOrcamento(int idEvento) {
        return eventoRepository.findStatusByEvento(idEvento);
    }

    public boolean pagarOrcamento(int idEvento) {
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);
        if (eventoOpt.isEmpty()) {
            return false;
        }
        Evento evento = eventoOpt.get();
        evento.setStatus("5");
        eventoRepository.save(evento);
        return true;
    }

    public List<OrcamentoPropDto> buscarOrcamentosDoBuffet(int idBuffet) {
        return eventoRepository.findAllOrcamentosByBuffet(idBuffet);
    }

}
