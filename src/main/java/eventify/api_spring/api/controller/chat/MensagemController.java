package eventify.api_spring.api.controller.chat;
import eventify.api_spring.domain.chat.Mensagem;
import eventify.api_spring.dto.chat.ChatListaDto;
import eventify.api_spring.dto.chat.MensagemDto;
import eventify.api_spring.service.chat.MensagemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<MensagemDto> mandarMensagemUsuario(@PathVariable int idUsuario, @PathVariable int idBuffet, @RequestParam String text) {
        Mensagem mensagem = mensagemService.mandarMensagem(idUsuario, idBuffet, text, false, null);
        if (mensagem == null) {
            return ResponseEntity.status(204).build();
        }
        MensagemDto mensagemDto = new MensagemDto(mensagem.getId(), mensagem.getMensagem(), mensagem.isMandadoPor(), mensagem.getData(), mensagem.getUsuario().getId(), mensagem.getBuffet().getId(), mensagem.getImagensDto());
        return ResponseEntity.status(200).body(mensagemDto);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/buffet/{idBuffet}/{idUsuario}")
    public ResponseEntity<MensagemDto> mandarMensagemBuffet(@PathVariable int idUsuario, @PathVariable int idBuffet, @RequestParam String text) {
        Mensagem mensagem = mensagemService.mandarMensagem(idUsuario, idBuffet, text, true, null);
        if (mensagem == null) {
            return ResponseEntity.status(204).build();
        }
        MensagemDto mensagemDto = new MensagemDto(mensagem.getId(), mensagem.getMensagem(), mensagem.isMandadoPor(), mensagem.getData(), mensagem.getUsuario().getId(), mensagem.getBuffet().getId(), mensagem.getImagensDto());
        return ResponseEntity.status(200).body(mensagemDto);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/usuario-imagem/{idUsuario}/{idBuffet}")
    public ResponseEntity<MensagemDto> mandarMensagemComImagemUsuario(@PathVariable int idUsuario, @PathVariable int idBuffet, @RequestParam String text, @RequestParam("file") List<MultipartFile> imagems) {
        Mensagem mensagem = mensagemService.mandarMensagem(idUsuario, idBuffet, text, false, imagems);
        if (mensagem == null) {
            return ResponseEntity.status(204).build();
        }
        MensagemDto mensagemDto = new MensagemDto(mensagem.getId(), mensagem.getMensagem(), mensagem.isMandadoPor(), mensagem.getData(), mensagem.getUsuario().getId(), mensagem.getBuffet().getId(), mensagem.getImagensDto());
        return ResponseEntity.status(200).body(mensagemDto);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MensagemDto>> listarPorUsuario(@PathVariable int idUsuario) {
        List<MensagemDto> mensagens = mensagemService.listarMensagemPorUsuario(idUsuario);
        
        if (!Objects.isNull(mensagens) && !mensagens.isEmpty()) {
            return ResponseEntity.ok(mensagens);
        }

        return noContent().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/buffet/{idBuffet}")
    public ResponseEntity<List<MensagemDto>> listarPorBuffet(@PathVariable int idBuffet) {
        List<MensagemDto> mensagens = mensagemService.listarMensagemPorBuffet(idBuffet);

        if (!Objects.isNull(mensagens) && !mensagens.isEmpty()) {
            return ok(mensagens);
        }

        return noContent().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/chat/{idUsuario}/{idBuffet}")
    public ResponseEntity<List<MensagemDto>> listarPorUsuarioBuffet(@PathVariable int idUsuario, @PathVariable int idBuffet) {
        List<MensagemDto> mensagens = mensagemService.listarMensagemPorUsuarioBuffet(idUsuario, idBuffet);
        
        if (mensagens.isEmpty()) {
            return noContent().build();
        }
        
        return ok(mensagens);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/chat/{idUsuario}")
    public ResponseEntity<List<ChatListaDto>> listarChatsDoUsuario(@PathVariable int idUsuario) {
        List<ChatListaDto> chats = mensagemService.listarChatsDoUsuario(idUsuario);

        if (chats.isEmpty()) {
            return noContent().build();
        }

        return ok(chats);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/chat")
    public ResponseEntity<List<ChatListaDto>> listarTodosOsChats() {
        List<ChatListaDto> chats = mensagemService.listarChat();

        if (chats.isEmpty()) {
            return noContent().build();
        }

        return ok().body(chats);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/check-chat/{idUsuario}/{idBuffet}")
    public ResponseEntity<Integer> checarQtdMensagens(@PathVariable int idUsuario, @PathVariable int idBuffet) {
        Integer qtdMensagens = mensagemService.checarQtdMensagens(idUsuario, idBuffet);

        if(Objects.isNull(qtdMensagens)) {
            return noContent().build();
        }

        return ok(qtdMensagens);
    }
}