package eventify.api_spring.api.controller.smartsync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventify.api_spring.domain.smartsync.LogTarefa;
import eventify.api_spring.dto.smartsync.LogTarefaCriacaoDto;
import eventify.api_spring.dto.smartsync.LogTarefaDto;
import eventify.api_spring.service.smartsync.LogTarefaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import static org.springframework.http.ResponseEntity.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/log-tarefa")
@SecurityRequirement(name = "requiredAuth")
public class LogTarefaController {
    @Autowired
    private LogTarefaService logTarefaService;

    @GetMapping
    public ResponseEntity<List<LogTarefaDto>> listarLogTarefa() {
        List<LogTarefaDto> logsTarefaDto = logTarefaService.listarLogTarefa();

        return ok(logsTarefaDto);
    }

    @GetMapping("/{idExecutor}")
    public ResponseEntity<List<LogTarefaDto>> retornarLogTarefa(@PathVariable Integer idExecutor, @RequestParam("isFuncionario") Boolean isFuncionario) {
        List<LogTarefaDto> logTarefaDto = logTarefaService.listarLogTarefaIndividual(idExecutor, isFuncionario);

        return ok(logTarefaDto);
    }

    @PostMapping
    public ResponseEntity<LogTarefa> criarLogTarefa(@RequestBody LogTarefaCriacaoDto logTarefaCriacaoDto) {
        LogTarefa logTarefaDto = logTarefaService.criarLogTarefa(logTarefaCriacaoDto);
        URI location = URI.create(String.format("/log-tarefa/%d", logTarefaDto.getId()));

        return created(location).build();
    }
}
