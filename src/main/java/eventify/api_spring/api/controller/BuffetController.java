package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Agenda;
import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Imagem;
import eventify.api_spring.dto.BuffetDtoResposta;
import eventify.api_spring.dto.BuffetInfoDto;
import eventify.api_spring.dto.DataDto;
import eventify.api_spring.dto.ImagemDTO;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.service.BuffetService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buffets")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="2. Buffet", description="Controller com os endpoints de buffet")
public class BuffetController {
    @Autowired
    private BuffetService buffetService;

    @GetMapping
    public ResponseEntity<List<BuffetDtoResposta>> listar() {
        List<BuffetDtoResposta> buffets = buffetService.listar();
        if (buffets.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(buffets);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<String>> listarTipoEventos() {
        List<String> tipos = buffetService.getTipoEventos();
        if (tipos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(tipos);
    }

    @GetMapping("/{idBuffet}/avaliacao")
    public ResponseEntity<Double> listarAvaliacoesEvento(@PathVariable int idBuffet) {
        return ResponseEntity.status(200).body(buffetService.getAvaliacaoEvento(idBuffet));
    }

    @GetMapping("/{idBuffet}/imagem")
    public ResponseEntity<List<ImagemDTO>> pegarCaminhoImagemEvento(@PathVariable int idBuffet) {
        return ResponseEntity.status(200).body(buffetService.pegarCaminhoImagem(idBuffet)
                .stream()
                .map(img -> new ImagemDTO(img.getId(),img.getCaminho(),img.getNome(), img.getTipo(), true,img.getDataUpload()))
                .toList());
    }

    @GetMapping("/datas/{idBuffet}")
    public ResponseEntity<List<DataDto>> pegarDatasOcupadas(@PathVariable int idBuffet) {
        return ResponseEntity.status(200).body(buffetService.pegarDatasOcupadas(idBuffet));
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<Buffet> cadastrar(@RequestBody @Valid Buffet buffet) {
        buffetService.cadastrar(buffet);
        return ResponseEntity.status(201).body(buffet);
    }

    @PutMapping("/{idBuffet}")
    public ResponseEntity<Buffet> atualizar(@PathVariable int idBuffet, @RequestBody @Valid Buffet buffet) {
        buffet.setId(idBuffet);
        buffetService.atualizar(buffet);
        return ResponseEntity.status(200).body(buffet);
    }

    @GetMapping("/abandono/{idBuffet}")
    public ResponseEntity<List<Long>> pegarTaxaDeAbandono(@PathVariable int idBuffet) {
        List<Long> result = buffetService.pegarTaxaDeAbandono(idBuffet);
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/satisfacao/{idBuffet}")
    public ResponseEntity<List<Object>> pegarTaxaDeSatisfacao(@PathVariable int idBuffet) {
        List<Object> result = buffetService.pegarTaxaDeSatisfacao(idBuffet);
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/financeiro/{idBuffet}")
    public ResponseEntity<List<Object>> pegarMovimentacaoFinanceira(@PathVariable int idBuffet) {
        List<Object> result = buffetService.pegarMovimentacaoFinanceira(idBuffet);
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/dados-financeiro/{idBuffet}")
    public ResponseEntity<List<Object[]>> pegarDadosFinanceiro(@PathVariable int idBuffet) {
        List<Object[]> result = buffetService.pegarDadosFinanceiro(idBuffet);
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/avaliacoes/{idBuffet}")
    public ResponseEntity<List<Object[]>> pegarAvaliacoes(@PathVariable int idBuffet) {
        List<Object[]> result = buffetService.pegarAvaliacoes(idBuffet);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/orcamentos/{idBuffet}")
    public ResponseEntity<List<Object[]>> pegarOrcamentos(@PathVariable int idBuffet) {
        List<Object[]> result = buffetService.pegarOrcamentos(idBuffet);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/infos")
    public ResponseEntity<Map<String, List<BuffetInfoDto>>> pegarBuffetInfo() {
        Map<String, List<BuffetInfoDto>> lista = buffetService.pegarBuffetInfoPorTipoEvento();
        return ResponseEntity.ok(lista);
    }

}
