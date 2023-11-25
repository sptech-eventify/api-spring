package eventify.api_spring.api.controller.smartsync;

import eventify.api_spring.dto.smartsync.SecaoBucketDto;
import eventify.api_spring.dto.smartsync.TarefaDto;
import eventify.api_spring.dto.smartsync.TarefaRespostaDto;
import eventify.api_spring.dto.smartsync.TarefaSecaoDto;
import eventify.api_spring.service.smartsync.TarefaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@SecurityRequirement(name = "requiredAuth")
public class TarefaController {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<TarefaRespostaDto>> exibirTodasTarefas() {
        List<TarefaRespostaDto> tarefas = tarefaService.exibirTodasTarefas();

        return ok(tarefas);
    }

    @GetMapping("/individual/{idUsuario}")
    public ResponseEntity<List<TarefaRespostaDto>> exibirTodasTarefasPorUsuarioId(@PathVariable Integer idUsuario, @RequestParam("isFuncionario") Boolean isFuncionario) {
        List<TarefaRespostaDto> tarefas = tarefaService.exibirTodasTarefasPorUsuarioId(idUsuario, isFuncionario);

        return ok(tarefas);
    }

    @GetMapping("/bucket/{idBucket}")
    public ResponseEntity<List<TarefaRespostaDto>> exibirTodasTarefasPorBucketId(@PathVariable Integer idBucket) {
        List<TarefaRespostaDto> tarefas = tarefaService.exibirTodasTarefasPorBucketId(idBucket);

        return ok(tarefas);
    }

    @GetMapping("/secoes")
    public ResponseEntity<List<TarefaSecaoDto>> exibirTodasTarefasPorSecao(@RequestParam("idBuffet") Integer idBuffet, @RequestParam("idEvento") Integer idEvento) {
        List<TarefaSecaoDto> tarefas = tarefaService.exibirTodasTarefasPorSecao(idBuffet, idEvento);

        return ok(tarefas);
    }

    @GetMapping("/secoes/{idSecao}")
    public ResponseEntity<SecaoBucketDto> exibirTodasTarefasPorSecaoIndividual(@RequestParam("idBuffet") Integer idBuffet, @RequestParam("idEvento") Integer idEvento, @PathVariable Integer idSecao) {
        SecaoBucketDto secao = tarefaService.exibirTodasTarefasPorSecaoIndividual(idBuffet, idEvento, idSecao);

        return ok(secao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaRespostaDto> exibirTarefaPorId(@PathVariable Integer id) {
        TarefaRespostaDto tarefa = tarefaService.exibirTarefaPorId(id);

        return ok(tarefa);
    }

    @PostMapping
    public ResponseEntity<TarefaDto> criarTarefa(@RequestBody TarefaDto tarefa) {
        TarefaDto tarefaAtualizada = tarefaService.criarTarefa(tarefa);

        return ok(tarefaAtualizada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDto> atualizarTarefa(@PathVariable Integer id, @RequestBody TarefaDto tarefa) {
        TarefaDto tarefaAtualizada = tarefaService.atualizarTarefa(id, tarefa);

        return ok(tarefaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Integer id) {
        tarefaService.deletarTarefa(id);

        return noContent().build();
    }
}