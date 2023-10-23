package eventify.api_spring.api.controller.usuario;

import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.dto.usuario.FuncionarioCadastrarDto;
import eventify.api_spring.service.usuario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<Funcionario>> exibirTodosFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.exibirTodosFuncionarios();

        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> exibirFuncionarioPorId(@PathVariable Integer id) {
        Funcionario funcionario = funcionarioService.exibirFuncionarioPorId(id);

        return ResponseEntity.ok(funcionario);
    }

    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody FuncionarioCadastrarDto funcionario) {
        Funcionario funcionarioCriado = funcionarioService.criarFuncionario(funcionario);

        return ResponseEntity.ok(funcionarioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Integer id) {
        Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionario(id);

        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Integer id) {
        funcionarioService.deletarFuncionario(id);

        return ResponseEntity.noContent().build();
    }
}
