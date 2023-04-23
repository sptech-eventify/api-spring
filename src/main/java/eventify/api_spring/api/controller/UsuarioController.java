package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.service.usuario.UsuarioService;
import eventify.api_spring.service.usuario.dto.UsuarioLoginDto;
import eventify.api_spring.service.usuario.dto.UsuarioTokenDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
// Controller recebe as requisições e as encaminha para o Service
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> lista = usuarioService.listar();
        if (lista.isEmpty()){
            return ResponseEntity.status(204).build();
        } return ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/id")
    public ResponseEntity<Optional<Usuario>> exibir(@RequestParam Integer id) {
        Optional<Usuario> resposta = usuarioService.exibir(id);

        if (resposta.isEmpty())
            return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(resposta);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody UsuarioCadastrarDTO usuario){
        usuarioService.cadastrar(usuario);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(int id){
        if (usuarioService.deletar(id)){
            return ResponseEntity.status(200).build();
        } return ResponseEntity.status(404).build();
    }

    @PutMapping
    public ResponseEntity<UsuarioCadastrarDTO> atualizar(int id, @RequestBody Usuario usuario){
        if (usuarioService.atualizar(id, usuario) != null){
            return ResponseEntity.status(200).body(usuarioService.atualizar(id, usuario));
        } return ResponseEntity.status(404).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){
        UsuarioTokenDto usuarioToken = this.usuarioService.autenticar(usuarioLoginDto);
        return ResponseEntity.status(200).body(usuarioToken);
    }

}
