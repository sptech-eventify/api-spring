package eventify.api_spring.api.controller.buffet;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.dto.buffet.BuffetDtoResposta;
import eventify.api_spring.dto.buffet.BuffetInfoDto;
import eventify.api_spring.dto.buffet.BuffetPublicDto;
import eventify.api_spring.dto.imagem.ImagemDTO;
import eventify.api_spring.dto.utils.DataDto;
import eventify.api_spring.service.buffet.BuffetService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/buffets")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name="2. Buffet", description="Controller com os endpoints de buffet")
public class BuffetController {

    @Autowired
    private BuffetService buffetService;

    @GetMapping
    public ResponseEntity<List<BuffetDtoResposta>> listar() {
        List<BuffetDtoResposta> buffets = buffetService.listar();

        if (buffets.isEmpty()) {
            return noContent().build();
        }

        return ok(buffets);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<Buffet> cadastrar(@RequestBody @Valid Buffet buffet) {
        Buffet buffetCadastrado = buffetService.cadastrar(buffet);

        return created(null).body(buffetCadastrado);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping("/{idBuffet}")
    public ResponseEntity<Buffet> atualizar(@PathVariable int idBuffet, @RequestBody @Valid Buffet buffet) {
       Buffet buffetAtualizado = buffetService.atualizar(idBuffet, buffet);

        return ok(buffetAtualizado);
    }

    // Criar delete de buffet

    @GetMapping("/{idBuffet}")
    public ResponseEntity<BuffetDtoResposta> buscarBuffet(@PathVariable int idBuffet) {
        BuffetDtoResposta buffet = buffetService.buscarBuffet(idBuffet);
        
        if(Objects.isNull(buffet)) {
            return notFound().build();
        }

        return ok(buffet);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<String>> listarTipoEventos() {
        List<String> tipos = buffetService.getTipoEventos();

        if (tipos.isEmpty()) {
            return noContent().build();
        }

        return ok(tipos);
    }

    @GetMapping("/{idBuffet}/avaliacao")
    public ResponseEntity<Double> listarAvaliacoesEvento(@PathVariable int idBuffet) {
        Double avaliacao = buffetService.getAvaliacaoEvento(idBuffet);
        
        if(Objects.isNull(avaliacao)) {
            return notFound().build();
        }

        return ok(avaliacao);
    }

    @GetMapping("/{idBuffet}/imagem")
    public ResponseEntity<List<ImagemDTO>> pegarCaminhoImagemEvento(@PathVariable int idBuffet) {
        List<ImagemDTO> imagens = buffetService.pegarCaminhoImagem(idBuffet);

        if (imagens.isEmpty()) {
            return noContent().build();
        } else if (Objects.isNull(imagens)) {
            return notFound().build();
        }
        
        return ok(imagens);
    }

    @GetMapping("/publico/{idBuffet}")
    public ResponseEntity<BuffetPublicDto> buscarBuffetPublico(@PathVariable int idBuffet) {
        BuffetPublicDto buffet = buffetService.buscarBuffetPublico(idBuffet);
        
        if(Objects.isNull(buffet)) {
            return notFound().build();
        }

        return ok(buffet);
    }

    @GetMapping("/publico/infos")
    public ResponseEntity<Map<String, List<BuffetInfoDto>>> pegarBuffetInfo() {
        Map<String, List<BuffetInfoDto>> buffets = buffetService.pegarBuffetInfoPorTipoEvento();
        
        if (Objects.isNull(buffets)) {
            return notFound().build();
        } else if (buffets.isEmpty()) {
            return noContent().build();
        }

        return ok(buffets);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/datas/{idBuffet}")
    public ResponseEntity<List<DataDto>> pegarDatasOcupadas(@PathVariable int idBuffet) {
        List<DataDto> datas = buffetService.pegarDatasOcupadas(idBuffet);

        if (datas.isEmpty()) {
            return noContent().build();
        } else if (Objects.isNull(datas)) {
            return notFound().build();
        }

        return ok(datas);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/orcamentos/{idBuffet}")
    public ResponseEntity<List<Object[]>> pegarOrcamentos(@PathVariable int idBuffet) {
        List<Object[]> orcamentos = buffetService.pegarOrcamentos(idBuffet);
        
        if (Objects.isNull(orcamentos)) {
            return notFound().build();
        } else if (orcamentos.isEmpty()) {
            return noContent().build();
        }

        return ok(orcamentos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/proprietario/{idUser}")
    public ResponseEntity<List<BuffetInfoDto>> pegarBuffetsProprietario(@PathVariable int idUser) {
        List<BuffetInfoDto> buffets = buffetService.pegarBuffetsProprietario(idUser);
        
        if (Objects.isNull(buffets)) {
            return notFound().build();
        } else if (buffets.isEmpty()) {
            return noContent().build();
        }

        return ok(buffets);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/abandono/{idBuffet}")
    public ResponseEntity<List<Long>> pegarTaxaDeAbandono(@PathVariable int idBuffet) {
        List<Long> taxaAbandono = buffetService.pegarTaxaDeAbandono(idBuffet);
        
        if (Objects.isNull(taxaAbandono)) {
            return notFound().build();
        } else if (taxaAbandono.isEmpty()) {
            return noContent().build();
        }

        return ok(taxaAbandono);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/satisfacao/{idBuffet}")
    public ResponseEntity<List<Object>> pegarTaxaDeSatisfacao(@PathVariable int idBuffet) {
        List<Object> taxaSatisfacao = buffetService.pegarTaxaDeSatisfacao(idBuffet);

         if (Objects.isNull(taxaSatisfacao)) {
            return notFound().build();
        } else if (taxaSatisfacao.isEmpty()) {
            return noContent().build();
        }

        return ok(taxaSatisfacao);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/financeiro/{idBuffet}")
    public ResponseEntity<List<Object>> pegarMovimentacaoFinanceira(@PathVariable int idBuffet) {
        List<Object> movimentacaoFinanceira = buffetService.pegarMovimentacaoFinanceira(idBuffet);
       
         if (Objects.isNull(movimentacaoFinanceira)) {
            return notFound().build();
        } else if (movimentacaoFinanceira.isEmpty()) {
            return noContent().build();
        }

        return ResponseEntity.ok(movimentacaoFinanceira);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/dados-financeiro/{idBuffet}")
    public ResponseEntity<List<Object[]>> pegarDadosFinanceiro(@PathVariable int idBuffet) {
        List<Object[]> dadosFinanceiro = buffetService.pegarDadosFinanceiro(idBuffet);
        
        if (Objects.isNull(dadosFinanceiro)) {
            return notFound().build();
        } else if (dadosFinanceiro.isEmpty()) {
            return noContent().build();
        }

        return ok(dadosFinanceiro);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/avaliacoes/{idBuffet}")
    public ResponseEntity<List<Object[]>> pegarAvaliacoes(@PathVariable int idBuffet) {
        List<Object[]> avaliacoes = buffetService.pegarAvaliacoes(idBuffet);
        
        if (Objects.isNull(avaliacoes)) {
            return notFound().build();
        } else if (avaliacoes.isEmpty()) {
            return noContent().build();
        }

        return ok(avaliacoes);
    }
}
