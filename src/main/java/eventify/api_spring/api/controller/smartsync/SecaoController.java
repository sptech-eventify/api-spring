package eventify.api_spring.api.controller.smartsync;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import eventify.api_spring.dto.smartsync.SecaoDto;
import eventify.api_spring.service.smartsync.TarefaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/secoes")
@SecurityRequirement(name = "requiredAuth")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = {"Access-Control-Expose-Headers", "Access-Token", "Uid"})
public class SecaoController {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<SecaoDto>> exibirDadosDaSecao(@RequestParam("idBuffet") Integer idBuffet, @RequestParam("idEvento") Integer idEvento) {
        List<SecaoDto> secoes = tarefaService.exibirDadosDaSecao(idBuffet, idEvento);

        return ok(secoes);
    }

}
