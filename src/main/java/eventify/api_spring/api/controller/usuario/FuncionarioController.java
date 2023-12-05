package eventify.api_spring.api.controller.usuario;

import eventify.api_spring.dto.usuario.FuncionarioCadastrarDto;
import eventify.api_spring.dto.usuario.FuncionarioDevolverDto;
import eventify.api_spring.service.usuario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<FuncionarioDevolverDto>> exibirTodosFuncionarios() {
        List<FuncionarioDevolverDto> funcionarios = funcionarioService.exibirTodosFuncionarios();

        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDevolverDto> exibirFuncionarioPorId(@PathVariable Integer id) {
        FuncionarioDevolverDto funcionario = funcionarioService.exibirFuncionarioPorId(id);

        return ResponseEntity.ok(funcionario);
    }

    @PostMapping
    public ResponseEntity<FuncionarioDevolverDto> criarFuncionario(@RequestBody FuncionarioCadastrarDto funcionario) {
        FuncionarioDevolverDto funcionarioCriado = funcionarioService.criarFuncionario(funcionario);
        URI location = URI.create(String.format("/funcionarios/%s", funcionarioCriado.getId()));

        return ResponseEntity.created(location).body(funcionarioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDevolverDto> atualizarFuncionario(@PathVariable Integer id) {
        FuncionarioDevolverDto funcionarioAtualizado = funcionarioService.atualizarFuncionario(id);

        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Integer id) {
        funcionarioService.deletarFuncionario(id);

        return ResponseEntity.noContent().build();
    }
}
