package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Servico;
import eventify.api_spring.repository.ServicoRepository;
import eventify.api_spring.service.ServicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="9. Serviço", description="Controller com os endpoints que controlam os serviços do sistema")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody Servico s) {
        Servico servico = this.servicoService.salvar(s);
        return ResponseEntity.status(201).body(s);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> exibirServico() {
         List<Servico> servicos = this.servicoService.listaServico();
         return ResponseEntity.status(200).body(servicos);
    }
}
