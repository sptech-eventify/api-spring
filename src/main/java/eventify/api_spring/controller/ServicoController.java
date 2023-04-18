package eventify.api_spring.controller;

import eventify.api_spring.domain.Servico;
import eventify.api_spring.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody Servico s) {
        Servico servico = this.servicoRepository.save(s);
        return ResponseEntity.status(201).body(s);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> exibirServico() {
         List<Servico> servicos = this.servicoRepository.findAll();
         return ResponseEntity.status(200).body(servicos);
    }
}
