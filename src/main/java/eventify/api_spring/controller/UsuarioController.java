package eventify.api_spring.controller;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.UsuarioCadastrarDTO;
import eventify.api_spring.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
// Controller recebe as requisições e as encaminha para o Service
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> exibir(@PathVariable Integer id) {
        Optional<Usuario> resposta = usuarioService.exibir(id);

        if (resposta.isEmpty())
            return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(resposta);
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody UsuarioCadastrarDTO usuario){
        Usuario resposta = usuarioService.cadastrar(usuario);
        return ResponseEntity.status(201).body(resposta);
    }

}
