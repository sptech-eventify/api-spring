package eventify.api_spring.api.controller.chat;
import eventify.api_spring.dto.chat.ChatListaDto;
import eventify.api_spring.dto.chat.MensagemDto;
import eventify.api_spring.service.chat.MensagemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/mensagens")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name="6. Mensagem", description="Controller com os endpoints que controlam os chats do sistema")
public class MensagemController {
    @Autowired
    private MensagemService mensagemService;

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/usuario/{idUsuario}/{idBuffet}")
    public ResponseEntity<MensagemDto> mandarMensagemUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idBuffet, @RequestParam String text) {
        MensagemDto mensagem = mensagemService.mandarMensagem(idUsuario, idBuffet, text, false, null);

        return ok(mensagem);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/buffet/{idBuffet}/{idUsuario}")
    public ResponseEntity<MensagemDto> mandarMensagemBuffet(@PathVariable Integer idUsuario, @PathVariable Integer idBuffet, @RequestParam String text) {
        MensagemDto mensagem = mensagemService.mandarMensagem(idUsuario, idBuffet, text, true, null);
        
        return ok(mensagem);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/usuario-imagem/{idUsuario}/{idBuffet}")
    public ResponseEntity<MensagemDto> mandarMensagemComImagemUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idBuffet, @RequestParam String text, @RequestParam("file") List<MultipartFile> imagems) {
        MensagemDto mensagem = mensagemService.mandarMensagem(idUsuario, idBuffet, text, false, imagems);
   
        return ok(mensagem);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MensagemDto>> listarPorUsuario(@PathVariable Integer idUsuario) {
        List<MensagemDto> mensagens = mensagemService.listarMensagemPorUsuario(idUsuario);
    
        return ok(mensagens);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/buffet/{idBuffet}")
    public ResponseEntity<List<MensagemDto>> listarPorBuffet(@PathVariable Integer idBuffet) {
        List<MensagemDto> mensagens = mensagemService.listarMensagemPorBuffet(idBuffet);

        return ok(mensagens);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/chat/{idUsuario}/{idBuffet}")
    public ResponseEntity<List<MensagemDto>> listarPorUsuarioBuffet(@PathVariable Integer idUsuario, @PathVariable Integer idBuffet) {
        List<MensagemDto> mensagens = mensagemService.listarMensagemPorUsuarioBuffet(idUsuario, idBuffet);
        
        return ok(mensagens);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/chat/{idUsuario}")
    public ResponseEntity<List<ChatListaDto>> listarChatsDoUsuario(@PathVariable Integer idUsuario) {
        List<ChatListaDto> chats = mensagemService.listarChatsDoUsuario(idUsuario);

        return ok(chats);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/chat")
    public ResponseEntity<List<ChatListaDto>> listarTodosOsChats() {
        List<ChatListaDto> chats = mensagemService.listarChat();

        return ok(chats);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/check-chat/{idUsuario}/{idBuffet}")
    public ResponseEntity<Integer> checarQtdMensagens(@PathVariable Integer idUsuario, @PathVariable Integer idBuffet) {
        Integer qtdMensagens = mensagemService.checarQtdMensagens(idUsuario, idBuffet);

        return ok(qtdMensagens);
    }
}