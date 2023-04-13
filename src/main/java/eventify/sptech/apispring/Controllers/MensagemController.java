package eventify.sptech.apispring.Controllers;

import eventify.sptech.apispring.Entities.Mensagem;
import eventify.sptech.apispring.Entities.Usuario;
import eventify.sptech.apispring.Repositories.MensagemRepository;
import eventify.sptech.apispring.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mensagems")
public class MensagemController {

    @Autowired
    private MensagemRepository mensagemRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Mensagem>> listarPorUsuario(@PathVariable int idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isPresent()) {
            Usuario remetente = usuarioOpt.get();
            List<Mensagem> mensagems = mensagemRepository.findAllByRemetente(remetente);
            if (mensagems.isEmpty()) {
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.status(200).body(mensagems);
        }
        return ResponseEntity.status(204).build();
    }

}
