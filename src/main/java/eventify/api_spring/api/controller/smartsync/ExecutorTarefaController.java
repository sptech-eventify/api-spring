package eventify.api_spring.api.controller.smartsync;

import eventify.api_spring.domain.smartsync.ExecutorTarefa;
import eventify.api_spring.service.smartsync.ExecutorTarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/executor-tarefas")
public class ExecutorTarefaController {

    @Autowired
    private ExecutorTarefaService executorTarefaService;

    @GetMapping
    public ResponseEntity<List<ExecutorTarefa>> exibirTodosExecutoresTarefas() {
        List<ExecutorTarefa> executorTarefas = executorTarefaService.exibirTodosExecutoresTarefas();

        return ResponseEntity.ok(executorTarefas);
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<ExecutorTarefa>> exibirTodosExecutoresTarefasPorFuncionarioId(@PathVariable Integer idFuncionario) {
        List<ExecutorTarefa> executorTarefas = executorTarefaService.exibirTodosExecutoresTarefasPorFuncionarioId(idFuncionario);

        return ResponseEntity.ok(executorTarefas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExecutorTarefa> exibirExecutorTarefaPorId(@PathVariable Integer id) {
        ExecutorTarefa executorTarefa = executorTarefaService.exibirExecutorTarefaPorId(id);

        return ResponseEntity.ok(executorTarefa);
    }

    @PostMapping
    public ResponseEntity<ExecutorTarefa> criarExecutorTarefa(@RequestBody @Valid ExecutorTarefa executorTarefa) {
        ExecutorTarefa executorTarefaCriado = executorTarefaService.criarExecutorTarefa(executorTarefa);

        return ResponseEntity.ok(executorTarefaCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExecutorTarefa> atualizarExecutorTarefa(@PathVariable Integer id) {
        ExecutorTarefa executorTarefaAtualizado = executorTarefaService.atualizarExecutorTarefa(id);

        return ResponseEntity.ok(executorTarefaAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarExecutorTarefa(@PathVariable Integer id) {
        executorTarefaService.deletarExecutorTarefa(id);

        return ResponseEntity.noContent().build();
    }
}
