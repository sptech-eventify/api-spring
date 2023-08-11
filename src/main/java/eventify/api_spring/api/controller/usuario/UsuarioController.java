package eventify.api_spring.api.controller.usuario;

import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDto;
import eventify.api_spring.dto.usuario.UsuarioDevolverDto;
import eventify.api_spring.dto.usuario.UsuarioInfoDto;
import eventify.api_spring.dto.usuario.UsuarioLoginDto;
import eventify.api_spring.dto.usuario.UsuarioTokenDto;
import eventify.api_spring.service.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.ResponseEntity.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name = "1. Usuário", description = "Controller com os endpoints de usuário, controlando o fluxo de entrada, saída, criação, atualização e remoção de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping
    public ResponseEntity<List<UsuarioDevolverDto>> listar() {
        List<UsuarioDevolverDto> usuarios = usuarioService.listar();
        
        return ok(usuarios);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioInfoDto> exibir(@PathVariable Integer idUsuario) {
        UsuarioInfoDto usuario = usuarioService.exibir(idUsuario);

        return ok(usuario);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrar(@RequestBody UsuarioCadastrarDto usuario) {
        UsuarioDevolverDto usuarioResposta = usuarioService.cadastrar(usuario);
        URI location = URI.create(String.format("/usuarios/%s", usuarioResposta.getId() ));

        return ResponseEntity.created(location).build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @DeleteMapping("/banir/{idUsuario}")
    public ResponseEntity<Void> banir(@PathVariable Integer idUsuario) {
        usuarioService.banir(idUsuario);

        return ok().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/desbanir/{idUsuario}")
    public ResponseEntity<Void> desbanir(@PathVariable Integer idUsuario) {
        usuarioService.desbanir(idUsuario);

        return ok().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping("/{idUsuario}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer idUsuario, @RequestBody Usuario usuario) {
        usuarioService.atualizar(idUsuario, usuario);

        return ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioToken = this.usuarioService.autenticar(usuarioLoginDto);

        return ok(usuarioToken);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PatchMapping("/logof/{idUsuario}")
    public ResponseEntity<Boolean> logof(@PathVariable Integer idUsuario) {
        return ok(usuarioService.logof(idUsuario));
    }

}