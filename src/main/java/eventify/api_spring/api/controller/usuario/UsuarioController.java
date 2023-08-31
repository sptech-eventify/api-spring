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

// Finalzada

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name = "Usuário", description = "Controller com os endpoints de usuário, controla o fluxo de entrada, saída, criação, atualização e remoção de usuários")
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
    public ResponseEntity<UsuarioInfoDto> exibir(@PathVariable Integer id) {
        UsuarioInfoDto usuario = usuarioService.exibir(id);

        return ok(usuario);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrar(@RequestBody UsuarioCadastrarDto usuario) {
        UsuarioDevolverDto usuarioResposta = usuarioService.cadastrar(usuario);
        URI location = URI.create(String.format("/usuarios/%s", usuarioResposta.getId() ));

        return ResponseEntity.created(location).build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @DeleteMapping("/banir/{id}")
    public ResponseEntity<Void> banir(@PathVariable Integer id) {
        usuarioService.banir(id);

        return ok().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/desbanir/{id}")
    public ResponseEntity<Void> desbanir(@PathVariable Integer id) {
        usuarioService.desbanir(id);

        return ok().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuarioService.atualizar(id, usuario);

        return ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioToken = this.usuarioService.autenticar(usuarioLoginDto);

        return ok(usuarioToken);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PatchMapping("/logof/{id}")
    public ResponseEntity<Boolean> logof(@PathVariable Integer id) {
        return ok(usuarioService.logof(id));
    }

}