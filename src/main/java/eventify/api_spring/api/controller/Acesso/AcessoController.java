package eventify.api_spring.api.controller.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import eventify.api_spring.service.acesso.AcessoService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/logAcesso")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @PostMapping("/{idPagina}")
    public ResponseEntity<Void> logAcesso(@PathVariable Integer idPagina) {
        acessoService.gerarLog(idPagina);

        return ok().build();
    }
}