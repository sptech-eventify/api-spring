package eventify.api_spring.api.controller.endereco;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.service.endereco.EnderecoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.ResponseEntity;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<Endereco>> exibirTodosEnderecos() {
        List<Endereco> enderecos = enderecoService.exibirTodosEnderecos();
        
        return ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> exibirEnderecoPorId(@PathVariable Integer id) {
        Endereco endereco = enderecoService.exibirEnderecoPorId(id);

        return ok(endereco);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<Void> cadastrarEndereco(@RequestBody Endereco endereco) {
        Endereco enderecoCadastrado = enderecoService.cadastrarEndereco(endereco);
        URI location = URI.create("/api/enderecos/" + enderecoCadastrado.getId());

        return created(location).build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(@PathVariable Integer id, @RequestBody Endereco endereco) {
        enderecoService.atualizarEndereco(id, endereco);

        return ok().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Integer id) {
        enderecoService.deletarEndereco(id);

        return noContent().build();
    }

}
