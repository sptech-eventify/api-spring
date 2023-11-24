package eventify.api_spring.api.controller.smartsync;

import eventify.api_spring.domain.smartsync.Comentario;
import eventify.api_spring.dto.smartsync.ComentarioCriacaoDto;
import eventify.api_spring.dto.smartsync.ComentarioRespostaDto;
import eventify.api_spring.service.smartsync.ComentarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

import java.net.URI;
import java.util.List;

@SecurityRequirement(name = "requiredAuth")
@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/tarefa/{id}")
    public ResponseEntity<List<ComentarioRespostaDto>> exibirComentarioPorIdTarefa(@PathVariable Integer id) {
        List<ComentarioRespostaDto> comentarios = comentarioService.exibirComentarioPorIdTarefa(id);

        return ok(comentarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioRespostaDto> exibirComentarioPorId(@PathVariable Integer id) {
        ComentarioRespostaDto comentario = comentarioService.exibirComentarioPorId(id);

        return ok(comentario);
    }

    @PostMapping
    public ResponseEntity<Comentario> criarComentario(@RequestBody ComentarioCriacaoDto comentario) {
        Comentario comentarioCriado = comentarioService.criarComentario(comentario);
        URI location = URI.create(String.format("/comentarios/%d", comentarioCriado.getId()));

        return created(location).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComentarioRespostaDto> atualizarComentario(@PathVariable Integer id, @RequestBody ComentarioCriacaoDto comentario) {
        ComentarioRespostaDto comentarioAtualizado = comentarioService.atualizarComentario(id, comentario);

        return ok(comentarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarComentario(@PathVariable Integer id) {
        comentarioService.deletarComentario(id);

        return noContent().build();
    }
}
