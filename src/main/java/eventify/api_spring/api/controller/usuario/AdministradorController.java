package eventify.api_spring.api.controller.usuario;

import eventify.api_spring.dto.endereco.EnderecoDto;
import eventify.api_spring.dto.usuario.UsuarioAdminDto;
import eventify.api_spring.dto.usuario.UsuarioBanidoDto;
import eventify.api_spring.dto.utils.ChurnDto;
import eventify.api_spring.dto.utils.ConversaoReservasDto;
import eventify.api_spring.dto.utils.ConversaoVisitantesDto;
import eventify.api_spring.dto.utils.PrecisaoFormularioDto;
import eventify.api_spring.dto.utils.RetencaoDto;
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

// Finalizada

@RestController
@SecurityRequirement(name = "requiredAuth")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioAdminDto>> pegarListaUsuarios() {
        List<UsuarioAdminDto> result = administradorService.pegarListaUsuarios();

        return ok(result);
    }

    @GetMapping("/usuarios/banidos")
    public ResponseEntity<List<UsuarioBanidoDto>> pegarUsuariosBanidos() {
        List<UsuarioBanidoDto> usuarios = administradorService.pegarUsuariosBanidos();

        return ok(usuarios);
    }

    @GetMapping("/enderecos")
    public ResponseEntity<List<EnderecoDto>> pegarListaEndereco() {
        List<EnderecoDto> enderecos = administradorService.pegarListaEndereco();
        
        return ok(enderecos);
    }

    @GetMapping("/dashboard/conversao-cadastros")
    public ResponseEntity<List<ConversaoVisitantesDto>> pegarConversaoCadastros() {
        List<ConversaoVisitantesDto> cadastros = administradorService.pegarConversaoCadastros();
        
        return ok(cadastros);
    }

    @GetMapping("/dashboard/precisao-formulario")
    public ResponseEntity<List<PrecisaoFormularioDto>> pegarPrecisaoFormulario() {
        List<PrecisaoFormularioDto> precisoes = administradorService.pegarPrecisaoFormulario();

        return ok(precisoes);
    }

    @GetMapping("/dashboard/conversao-reservas")
    public ResponseEntity<List<ConversaoReservasDto>> pegarConversaoReserva() {
        List<ConversaoReservasDto> conversaoReservas = administradorService.pegarConversaoReservas();

        return ok(conversaoReservas);
    }

    @GetMapping("/dashboard/churn")
    public ResponseEntity<List<ChurnDto>> pegarChurn() {
        List<ChurnDto> churns = administradorService.pegarChurn();

        return ok(churns);
    }

    @GetMapping("/dashboard/retencao/buffets")
    public ResponseEntity<List<RetencaoDto>> pegarRetencaoBuffets() {
        List<RetencaoDto> retencoes = administradorService.pegarRetencaoBuffets();

        return ok(retencoes);
    }

    @GetMapping("/dashboard/retencao/formularios")
    public ResponseEntity<List<RetencaoDto>> pegarRetencaoFormulario() {
        List<RetencaoDto> retencoes = administradorService.pegarRetencaoFormulario();

        return ok(retencoes);
    }

    @GetMapping("/dashboard/retencao/usuarios")
    public ResponseEntity<List<RetencaoDto>> pegarRetencaoUsuarios() {
        List<RetencaoDto> retencoes = administradorService.pegarRetencaoUsuarios();

        return ok(retencoes);
    }
}