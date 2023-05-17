package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioDevolverDTO;
import eventify.api_spring.service.usuario.UsuarioService;
import eventify.api_spring.service.usuario.dto.UsuarioLoginDto;
import eventify.api_spring.service.usuario.dto.UsuarioTokenDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "1. Usuário", description = "Controller com os endpoints de usuário, controlando o fluxo de entrada, saída, criação, atualização e remoção de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping
    public ResponseEntity<List<UsuarioDevolverDTO>> listar() {
        List<Usuario> lista = usuarioService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<UsuarioDevolverDTO> listaDTO = new ArrayList<>();

        for (Usuario user : lista) {
            listaDTO.add(new UsuarioDevolverDTO(user.getId(), user.getNome(), user.getEmail()));
        }

        return ResponseEntity.status(200).body(listaDTO);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/id")
    public ResponseEntity<UsuarioDevolverDTO> exibir(@RequestParam Integer id) {
        Optional<Usuario> resposta = usuarioService.exibir(id);

        if (resposta.isEmpty())
            return ResponseEntity.status(204).build();

        UsuarioDevolverDTO user = new UsuarioDevolverDTO(resposta.get().getId(), resposta.get().getNome(), resposta.get().getEmail());

        return ResponseEntity.status(200).body(user);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDevolverDTO> cadastrar(@RequestBody @Valid UsuarioCadastrarDTO usuario) {
        return ResponseEntity.status(201).body(usuarioService.cadastrar(usuario));
    }

    @SecurityRequirement(name = "requiredAuth")
    @DeleteMapping("/banir")
    public ResponseEntity<Void> banir(int id) {
        if (usuarioService.banir(id)) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/desbanir")
    public ResponseEntity<Void> desbanir(int id) {
        if (usuarioService.desbanir(id)) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping
    public ResponseEntity<UsuarioCadastrarDTO> atualizar(@RequestParam int id, @RequestBody Usuario usuario) {
        if (usuarioService.atualizar(id, usuario) != null) {
            return ResponseEntity.status(200).body(usuarioService.atualizar(id, usuario));
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioToken = this.usuarioService.autenticar(usuarioLoginDto);
        return ResponseEntity.status(200).body(usuarioToken);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PatchMapping("/logof")
    public ResponseEntity<Void> logof(@RequestParam int id) {
        if (usuarioService.logof(id)) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
