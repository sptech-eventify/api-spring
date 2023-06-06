package eventify.api_spring.api.controller;

import eventify.api_spring.api.assets.Fila;
import eventify.api_spring.api.assets.Pilha;
import eventify.api_spring.domain.Endereco;
import eventify.api_spring.dto.usuario.UsuarioAdminDto;
import eventify.api_spring.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/conversao-cadastros")
    public ResponseEntity<List<Object[]>> pegarConversaoCadastros() {
        List<Object[]> result = adminService.pegarConversaoCadastros();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/precisao-formulario")
    public ResponseEntity<List<Object[]>> pegarPrecisaoFormulario() {
        List<Object[]> result = adminService.pegarPrecisaoFormulario();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/conversao-reservas")
    public ResponseEntity<List<Object[]>> pegarConversaoReserva() {
        List<Object[]> result = adminService.pegarConversaoReservas();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/churn")
    public ResponseEntity<List<Object[]>> pegarChurn() {
        List<Object[]> result = adminService.pegarChurn();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/retencao/buffets")
    public ResponseEntity<List<Object[]>> pegarRetencaoBuffets() {
        List<Object[]> result = adminService.pegarRetencaoBuffets();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/retencao/formularios")
    public ResponseEntity<List<Object[]>> pegarRetencaoFormulario() {
        List<Object[]> result = adminService.pegarRetencaoFormulario();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/retencao/usuarios")
    public ResponseEntity<List<Object[]>> pegarRetencaoUsuarios() {
        List<Object[]> result = adminService.pegarRetencaoUsuarios();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioAdminDto>> pegarListaUsuarios() {
        List<UsuarioAdminDto> result = adminService.pegarListaUsuarios();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/usuarios/banidos")
    public ResponseEntity<List<Object[]>> pegarUsuariosBanidos() {
        List<Object[]> result = adminService.pegarUsuariosBanidos();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Pilha<Object[]> pilha = new Pilha<>(result.size());
        for (Object[] obj : result) {
            pilha.push(obj);
        }

        List<Object[]> valoresPilha = pilha.getValores();

        return ResponseEntity.ok(valoresPilha);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/enderecos")
    public ResponseEntity<List<Endereco>> pegarListaEndereco() {
        List<Endereco> enderecos = adminService.pegarListaEndereco();
        if (enderecos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }else {
            return ResponseEntity.status(200).body(enderecos);
        }
    }




}
