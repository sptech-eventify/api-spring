package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.smartsync.Comentario;
import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.dto.smartsync.ComentarioCriacaoDto;
import eventify.api_spring.dto.smartsync.ComentarioRespostaDto;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.repository.ComentarioRepository;
import eventify.api_spring.repository.FuncionarioRepository;
import eventify.api_spring.repository.TarefaRepository;
import eventify.api_spring.repository.UsuarioRepository;
import eventify.api_spring.service.usuario.FuncionarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<ComentarioRespostaDto> exibirComentarioPorIdTarefa(Integer idTarefa) {
        Tarefa tarefa = tarefaRepository.findById(idTarefa).orElseThrow(() -> new NotFoundException("Tarefa não encontrada"));

        List<Comentario> comentarios = comentarioRepository.findAllByTarefaAndIsVisivel(tarefa, true);

        if (comentarios.isEmpty()) {
            throw new NoContentException("Não há comentários para essa tarefa");
        }

        List<ComentarioRespostaDto> comentariosDto = new ArrayList<>();
        for (Comentario comentario : comentarios) {
            ComentarioRespostaDto comentarioRespostaDto = new ComentarioRespostaDto();

            comentarioRespostaDto.setId(comentario.getId());
            comentarioRespostaDto.setMensagem(comentario.getMensagem());
            comentarioRespostaDto.setDataCriacao(comentario.getDataCriacao());
            comentarioRespostaDto.setIsVisivel(comentario.getIsVisivel());

            ComentarioRespostaDto.Remetente remetente = comentarioRespostaDto.new Remetente();

            if (comentario.getFuncionario() != null) {
                remetente.setId(comentario.getFuncionario().getId());
                remetente.setNome(comentario.getFuncionario().getNome());
                remetente.setFoto(comentario.getFuncionario().getImagem());
                remetente.setIsFuncionario(true);
            } else {
                remetente.setId(comentario.getUsuario().getId());
                remetente.setNome(comentario.getUsuario().getNome());
                remetente.setFoto(comentario.getUsuario().getImagem());
                remetente.setIsFuncionario(false);
            }

            comentarioRespostaDto.setRemetente(remetente);
            comentariosDto.add(comentarioRespostaDto);
        }

        return comentariosDto;
    }

    public ComentarioRespostaDto exibirComentarioPorId(Integer id) {
        Comentario comentario = comentarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        ComentarioRespostaDto comentarioRespostaDto = new ComentarioRespostaDto();

        comentarioRespostaDto.setId(comentario.getId());
        comentarioRespostaDto.setMensagem(comentario.getMensagem());
        comentarioRespostaDto.setDataCriacao(comentario.getDataCriacao());
        comentarioRespostaDto.setIsVisivel(comentario.getIsVisivel());
        
        ComentarioRespostaDto.Remetente remetente = comentarioRespostaDto.new Remetente();

        if (comentario.getFuncionario() != null) {
            remetente.setId(comentario.getFuncionario().getId());
            remetente.setNome(comentario.getFuncionario().getNome());
            remetente.setFoto(comentario.getFuncionario().getImagem());
            remetente.setIsFuncionario(true);
        } else {
            remetente.setId(comentario.getUsuario().getId());
            remetente.setNome(comentario.getUsuario().getNome());
            remetente.setFoto(comentario.getUsuario().getImagem());
            remetente.setIsFuncionario(false);
        }

        comentarioRespostaDto.setRemetente(remetente);

        return comentarioRespostaDto;
    }


    public Comentario criarComentario(ComentarioCriacaoDto comentarioCriacao) {
        Tarefa tarefa = tarefaRepository.findById(comentarioCriacao.getIdTarefa()).orElseThrow(() -> new NotFoundException("Tarefa não encontrada"));

        Comentario comentario = new Comentario();

        comentario.setMensagem(comentarioCriacao.getMensagem());
        comentario.setDataCriacao(comentarioCriacao.getDataCriacao());
        comentario.setIsVisivel(comentarioCriacao.getIsVisivel());
        comentario.setTarefa(tarefa);

        if (comentarioCriacao.getIdFuncionario() != null) {
            comentario.setFuncionario(funcionarioRepository.findById(comentarioCriacao.getIdFuncionario()).orElseThrow(() -> new NotFoundException("Funcionário não encontrado")));
        } else {
            comentario.setUsuario(usuarioRepository.findById(comentarioCriacao.getIdUsuario()).orElseThrow(() -> new NotFoundException("Usuário não encontrado")));
        }

        return comentarioRepository.save(comentario);
    }

    public ComentarioRespostaDto atualizarComentario(Integer id, ComentarioCriacaoDto comentarioCriacao) {
        Comentario comentario = comentarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        comentario.setMensagem(comentarioCriacao.getMensagem());
        comentario.setDataCriacao(comentarioCriacao.getDataCriacao());
        comentario.setIsVisivel(comentarioCriacao.getIsVisivel());
        
        if (comentarioCriacao.getIdFuncionario() != null) {
            comentario.setFuncionario(funcionarioRepository.findById(comentarioCriacao.getIdFuncionario()).orElseThrow(() -> new NotFoundException("Funcionário não encontrado")));
        } else {
            comentario.setUsuario(usuarioRepository.findById(comentarioCriacao.getIdUsuario()).orElseThrow(() -> new NotFoundException("Usuário não encontrado")));
        }

        comentarioRepository.save(comentario);

        ComentarioRespostaDto comentarioRespostaDto = new ComentarioRespostaDto();
        
        comentarioRespostaDto.setId(comentario.getId());
        comentarioRespostaDto.setMensagem(comentario.getMensagem());
        comentarioRespostaDto.setDataCriacao(comentario.getDataCriacao());
        comentarioRespostaDto.setIsVisivel(comentario.getIsVisivel());

        ComentarioRespostaDto.Remetente remetente = comentarioRespostaDto.new Remetente();
        
        if (comentario.getFuncionario() != null) {
            remetente.setId(comentario.getFuncionario().getId());
            remetente.setNome(comentario.getFuncionario().getNome());
            remetente.setFoto(comentario.getFuncionario().getImagem());
            remetente.setIsFuncionario(true);
        } else {
            remetente.setId(comentario.getUsuario().getId());
            remetente.setNome(comentario.getUsuario().getNome());
            remetente.setFoto(comentario.getUsuario().getImagem());
            remetente.setIsFuncionario(false);
        }

        comentarioRespostaDto.setRemetente(remetente);

        return comentarioRespostaDto;
    }

    public void deletarComentario(Integer id) {
        Comentario comentario = comentarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        comentario.setIsVisivel(false);

        comentarioRepository.save(comentario);
    }
}
