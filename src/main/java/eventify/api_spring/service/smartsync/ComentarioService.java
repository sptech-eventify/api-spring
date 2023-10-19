package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.smartsync.Comentario;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<Comentario> exibirTodosComentarios() {
        List<Comentario> comentarios = comentarioRepository.findAll();

        if(comentarios.isEmpty()){
            throw new NoContentException("Não há comentários cadastrados");
        }

        return comentarios;
    }

    public Comentario exibirComentarioPorId(Integer id) {
        Comentario comentario = comentarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        return comentario;
    }


    public Comentario criarComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    public Comentario atualizarComentario(Integer id){
        Comentario comentario = comentarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        return comentarioRepository.save(comentario);
    }

    public void deletarComentario(Integer id) {
        Comentario comentario = comentarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

        comentario.setIsVisivel(false);

        comentarioRepository.save(comentario);
    }
}
