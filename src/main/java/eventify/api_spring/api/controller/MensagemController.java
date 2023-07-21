package eventify.api_spring.api.controller;
import eventify.api_spring.domain.chat.Mensagem;
import eventify.api_spring.dto.chat.ChatListaDto;
import eventify.api_spring.dto.chat.MensagemDto;
import eventify.api_spring.service.chat.MensagemServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="6. Mensagem", description="Controller com os endpoints que controlam os chats do sistema")
public class MensagemController {

    @Autowired
    private MensagemServices mensagemServices;

    @PostMapping("/usuario/{idUsuario}/{idBuffet}")
    public ResponseEntity<MensagemDto> mandarMensagemUsuario(@PathVariable int idUsuario, @PathVariable int idBuffet, @RequestParam String text) {
        Mensagem mensagem = mensagemServices.mandarMensagem(idUsuario, idBuffet, text, false, null);
        if (mensagem == null) {
            return ResponseEntity.status(204).build();
        }
        MensagemDto mensagemDto = new MensagemDto(mensagem.getId(), mensagem.getMensagem(), mensagem.isMandadoPor(), mensagem.getData(), mensagem.getUsuario().getId(), mensagem.getBuffet().getId(), mensagem.getImagensDto());
        return ResponseEntity.status(200).body(mensagemDto);
    }

    @PostMapping("/buffet/{idBuffet}/{idUsuario}")
    public ResponseEntity<MensagemDto> mandarMensagemBuffet(@PathVariable int idUsuario, @PathVariable int idBuffet, @RequestParam String text) {
        Mensagem mensagem = mensagemServices.mandarMensagem(idUsuario, idBuffet, text, true, null);
        if (mensagem == null) {
            return ResponseEntity.status(204).build();
        }
        MensagemDto mensagemDto = new MensagemDto(mensagem.getId(), mensagem.getMensagem(), mensagem.isMandadoPor(), mensagem.getData(), mensagem.getUsuario().getId(), mensagem.getBuffet().getId(), mensagem.getImagensDto());
        return ResponseEntity.status(200).body(mensagemDto);
    }

    @PostMapping("/usuario-imagem/{idUsuario}/{idBuffet}")
    public ResponseEntity<MensagemDto> mandarMensagemComImagemUsuario(@PathVariable int idUsuario, @PathVariable int idBuffet, @RequestParam String text, @RequestParam("file") List<MultipartFile> imagems) {
        Mensagem mensagem = mensagemServices.mandarMensagem(idUsuario, idBuffet, text, false, imagems);
        if (mensagem == null) {
            return ResponseEntity.status(204).build();
        }
        MensagemDto mensagemDto = new MensagemDto(mensagem.getId(), mensagem.getMensagem(), mensagem.isMandadoPor(), mensagem.getData(), mensagem.getUsuario().getId(), mensagem.getBuffet().getId(), mensagem.getImagensDto());
        return ResponseEntity.status(200).body(mensagemDto);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MensagemDto>> listarPorUsuario(@PathVariable int idUsuario) {
        List<MensagemDto> mensagems = mensagemServices.listarMensagemPorUsuario(idUsuario);
        if (mensagems != null && !mensagems.isEmpty()) {
            return ResponseEntity.status(200).body(mensagems);
        }
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/buffet/{idBuffet}")
    public ResponseEntity<List<MensagemDto>> listarPorBuffet(@PathVariable int idBuffet) {
        List<MensagemDto> mensagems = mensagemServices.listarMensagemPorBuffet(idBuffet);
        if (mensagems != null && !mensagems.isEmpty()) {
            return ResponseEntity.status(200).body(mensagems);
        }
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/chat/{idUsuario}/{idBuffet}")
    public ResponseEntity<List<MensagemDto>> listarPorUsuarioBuffet(@PathVariable int idUsuario, @PathVariable int idBuffet) {
        List<MensagemDto> mensagems = mensagemServices.listarMensagemPorUsuarioBuffet(idUsuario, idBuffet);
        if (mensagems.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(mensagems);
    }

    @GetMapping("/chat/{idUsuario}")
    public ResponseEntity<List<ChatListaDto>> listarChatsDoUsuario(@PathVariable int idUsuario) {
        List<ChatListaDto> lista = mensagemServices.listarChatsDoUsuario(idUsuario);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/chat")
    public ResponseEntity<List<ChatListaDto>> listarTodosOsChats() {
        List<ChatListaDto> lista = mensagemServices.listarChat();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/check-chat/{idUsuario}/{idBuffet}")
    public ResponseEntity<Integer> checarQtdMensagens(@PathVariable int idUsuario, @PathVariable int idBuffet) {
        return ResponseEntity.ok().body(mensagemServices.checarQtdMensagens(idUsuario, idBuffet));
    }

}
