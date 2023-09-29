package eventify.api_spring.api.controller.pagamento;


import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.service.buffet.BuffetService;
import eventify.api_spring.service.pagamento.PagamentoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/pagamento")
@CrossOrigin(origins = {"http://localhost:3000", "http://26.69.189.151:5173"})
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<Void> criarPagamento(@RequestBody @Valid String token) {
        pagamentoService.criarPagamento(token);
        return created(URI.create("")).build();
    }

}
