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

import eventify.api_spring.domain.smartsync.LogAcessoTarefa;
import eventify.api_spring.dto.smartsync.LogAcessoTarefaCriacaoDto;
import eventify.api_spring.dto.smartsync.LogAcessoTarefaDto;
import eventify.api_spring.service.smartsync.LogAcessoTarefaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import static org.springframework.http.ResponseEntity.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/log-acesso-tarefa")
@SecurityRequirement(name = "requiredAuth")
public class LogAcessoTarefaController {
    @Autowired
    private LogAcessoTarefaService logAcessoTarefaService;

    @GetMapping("/{idExecutor}")
    public ResponseEntity<List<LogAcessoTarefaDto>> retornarLogAcessoTarefa(@PathVariable Integer idExecutor, @RequestParam("isFuncionario") Boolean isFuncionario) {
        List<LogAcessoTarefaDto> logAcessoTarefaDto = logAcessoTarefaService.retornarLogAcessoTarefa(idExecutor, isFuncionario);

        return ok(logAcessoTarefaDto);
    }

    @PostMapping
    public ResponseEntity<LogAcessoTarefaDto> criarLogAcessoTarefa(@RequestBody LogAcessoTarefaCriacaoDto logAcessoTarefaCriacaoDto) {
        LogAcessoTarefa logAcessoTarefaDto = logAcessoTarefaService.salvarLogAcessoTarefa(logAcessoTarefaCriacaoDto);
        URI location = URI.create(String.format("/log-acesso-tarefa/%d", logAcessoTarefaDto.getId()));

        return created(location).build();
    }
}
