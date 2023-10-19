package eventify.api_spring.api.controller.smartsync;

import eventify.api_spring.domain.smartsync.Comentario;
import eventify.api_spring.service.smartsync.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<List<Comentario>> exibirTodosComentarios() {
        List<Comentario> comentarios = comentarioService.exibirTodosComentarios();

        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> exibirComentarioPorId(@PathVariable Integer id) {
        Comentario comentario = comentarioService.exibirComentarioPorId(id);

        return ResponseEntity.ok(comentario);
    }

    @PostMapping
    public ResponseEntity<Comentario> criarComentario(@RequestBody @Valid Comentario comentario) {
        Comentario comentarioCriado = comentarioService.criarComentario(comentario);

        return ResponseEntity.ok(comentarioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comentario> atualizarComentario(@PathVariable Integer id) {
        Comentario comentarioAtualizado = comentarioService.atualizarComentario(id);

        return ResponseEntity.ok(comentarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarComentario(@PathVariable Integer id) {
        comentarioService.deletarComentario(id);

        return ResponseEntity.noContent().build();
    }
}
