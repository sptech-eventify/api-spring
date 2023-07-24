package eventify.api_spring.api.controller.usuario;

import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioDevolverDTO;
import eventify.api_spring.dto.usuario.UsuarioInfoDto;
import eventify.api_spring.dto.usuario.UsuarioLoginDto;
import eventify.api_spring.dto.usuario.UsuarioTokenDto;
import eventify.api_spring.service.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.ResponseEntity.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name = "1. Usuário", description = "Controller com os endpoints de usuário, controlando o fluxo de entrada, saída, criação, atualização e remoção de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping
    public ResponseEntity<List<UsuarioDevolverDTO>> listar() {
        List<UsuarioDevolverDTO> usuarios = usuarioService.listar();
        
        if (usuarios.isEmpty()) {
            return noContent().build();
        }

        return ok(usuarios);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioInfoDto> exibir(@PathVariable Integer id) {
        UsuarioInfoDto usuario = usuarioService.exibir(id);

        if (Objects.isNull(usuario)) {
            return notFound().build();
        }

        return ok(usuario);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid UsuarioCadastrarDTO usuario) {
        UsuarioDevolverDTO usuarioResposta = usuarioService.cadastrar(usuario);

        if (Objects.isNull(usuarioResposta)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.created(null).build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @DeleteMapping("/banir")
    public ResponseEntity<Void> banir(int id) {
        if (usuarioService.banir(id)) {
            return noContent().build();
        }

        return notFound().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/desbanir")
    public ResponseEntity<Void> desbanir(int id) {
        if (usuarioService.desbanir(id)) {
            return noContent().build();
        }

        return notFound().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping
    public ResponseEntity<UsuarioCadastrarDTO> atualizar(@RequestParam int id, @RequestBody Usuario usuario) {
        UsuarioCadastrarDTO usuarioAtualizado = usuarioService.atualizar(id, usuario);

        if (Objects.isNull(usuarioAtualizado)) {
            return notFound().build();
        }

        return ok(usuarioAtualizado);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioToken = this.usuarioService.autenticar(usuarioLoginDto);

        if (Objects.isNull(usuarioToken)) {
            return notFound().build();
        }

        return ok(usuarioToken);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PatchMapping("/logof")
    public ResponseEntity<Void> logof(@RequestParam int id) {
        if (usuarioService.logof(id)) {
            return ok().build();
        }

        return notFound().build();
    }

}