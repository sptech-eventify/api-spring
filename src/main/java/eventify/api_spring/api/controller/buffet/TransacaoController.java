package eventify.api_spring.api.controller.buffet;


import eventify.api_spring.domain.buffet.Transacao;
import eventify.api_spring.service.buffet.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<List<Transacao>> exibirTodasTransacoes(){
        List<Transacao> transacaos = transacaoService.exibirTodasTransacoes();

        return ResponseEntity.ok(transacaos);
    }

    @GetMapping("/buffet/{idBuffet}")
    public ResponseEntity<List<Transacao>> exibirTodasTransacoesPorBuffetId(@PathVariable Integer idBuffet){
        List<Transacao> transacaos = transacaoService.exibirTodasTransacoesPorBuffetId(idBuffet);

        return ResponseEntity.ok(transacaos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> exibirTransacaoPorId(@PathVariable Integer id){
        Transacao transacao = transacaoService.exibirTransacaoPorId(id);

        return ResponseEntity.ok(transacao);
    }

    @PostMapping
    public ResponseEntity<Transacao> criarTransacao(@RequestBody @Valid Transacao transacao){
        Transacao transacaoCriado = transacaoService.criarTransacao(transacao);

        return ResponseEntity.ok(transacaoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> atualizarTransacao(@PathVariable Integer id){
        Transacao transacaoAtualizado = transacaoService.atualizarTransacao(id);

        return ResponseEntity.ok(transacaoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable Integer id){
        transacaoService.deletarTransacao(id);

        return ResponseEntity.noContent().build();
    }
}
