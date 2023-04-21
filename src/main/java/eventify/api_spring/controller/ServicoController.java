package eventify.api_spring.controller;

import eventify.api_spring.domain.Servico;
import eventify.api_spring.repository.ServicoRepository;
import eventify.api_spring.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = "http://localhost:3000")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

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
