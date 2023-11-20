package eventify.api_spring.api.controller.smartsync;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eventify.api_spring.dto.smartsync.SecaoDto;
import eventify.api_spring.service.smartsync.TarefaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secoes")
@SecurityRequirement(name = "requiredAuth")
public class SecaoController {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<SecaoDto>> exibirDadosDaSecao(@RequestParam("idBuffet") Integer idBuffet, @RequestParam("idEvento") Integer idEvento) {
        List<SecaoDto> secoes = tarefaService.exibirDadosDaSecao(idBuffet, idEvento);

        return ResponseEntity.ok(secoes);
    }

}
