package eventify.api_spring.api.controller.usuario;

import eventify.api_spring.dto.usuario.UsuarioAdminDto;
import eventify.api_spring.service.usuario.AdministradorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioAdminDto>> pegarListaUsuarios() {
        List<UsuarioAdminDto> result = administradorService.pegarListaUsuarios();
        
        if (result.isEmpty()) {
            return noContent().build();
        }

        return ok(result);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/usuarios/banidos")
    public ResponseEntity<List<Object[]>> pegarUsuariosBanidos() {
        List<Object[]> result = administradorService.pegarUsuariosBanidos();

        if (result.isEmpty()) {
            return noContent().build();
        }

        return ok(result);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/enderecos")
    public ResponseEntity<List<Object[]>> pegarListaEndereco() {
        List<Object[]> enderecos = administradorService.pegarListaEndereco();

        if (enderecos.isEmpty()) {
            return noContent().build();
        }
        
        return ok(enderecos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/conversao-cadastros")
    public ResponseEntity<List<Object[]>> pegarConversaoCadastros() {
        List<Object[]> result = administradorService.pegarConversaoCadastros();

        if (result.isEmpty()) {
            return noContent().build();
        }
        
        return ok(result);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/precisao-formulario")
    public ResponseEntity<List<Object[]>> pegarPrecisaoFormulario() {
        List<Object[]> result = administradorService.pegarPrecisaoFormulario();

        if (result.isEmpty()) {
            return noContent().build();
        }

        return ok(result);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/conversao-reservas")
    public ResponseEntity<List<Object[]>> pegarConversaoReserva() {
        List<Object[]> result = administradorService.pegarConversaoReservas();

        if (result.isEmpty()) {
            return noContent().build();
        }

        return ok(result);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/churn")
    public ResponseEntity<List<Object[]>> pegarChurn() {
        List<Object[]> result = administradorService.pegarChurn();
        if (result.isEmpty()) {
            return noContent().build();
        }

        return ok(result);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/retencao/buffets")
    public ResponseEntity<List<Object[]>> pegarRetencaoBuffets() {
        List<Object[]> result = administradorService.pegarRetencaoBuffets();

        if (result.isEmpty()) {
            return noContent().build();
        }

        return ok(result);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/retencao/formularios")
    public ResponseEntity<List<Object[]>> pegarRetencaoFormulario() {
        List<Object[]> result = administradorService.pegarRetencaoFormulario();

        if (result.isEmpty()) {
            return noContent().build();
        }

        return ok(result);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/retencao/usuarios")
    public ResponseEntity<List<Object[]>> pegarRetencaoUsuarios() {
        List<Object[]> result = administradorService.pegarRetencaoUsuarios();
        if (result.isEmpty()) {
            return noContent().build();
        }

        return ok(result);
    }
}
