package eventify.api_spring.api.controller.smartsync;

import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.service.smartsync.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<Tarefa>> exibirTodasTarefas() {
        List<Tarefa> tarefas = tarefaService.exibirTodasTarefas();

        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/bucket/{idBucket}")
    public ResponseEntity<List<Tarefa>> exibirTodasTarefasPorBucketId(@RequestBody Integer idBucket) {
        List<Tarefa> tarefas = tarefaService.exibirTodasTarefasPorBucketId(idBucket);

        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> exibirTarefaPorId(@PathVariable Integer id) {
        Tarefa tarefa = tarefaService.exibirTarefaPorId(id);

        return ResponseEntity.ok(tarefa);
    }

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody @Valid Tarefa tarefa) {
        Tarefa tarefaCriada = tarefaService.criarTarefa(tarefa);

        return ResponseEntity.ok(tarefaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Integer id) {
        Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(id);

        return ResponseEntity.ok(tarefaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Integer id) {
        tarefaService.deletarTarefa(id);

        return ResponseEntity.noContent().build();
    }

}
