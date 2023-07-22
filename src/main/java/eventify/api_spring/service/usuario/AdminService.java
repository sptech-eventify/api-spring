package eventify.api_spring.service.usuario;

import eventify.api_spring.dto.usuario.UsuarioAdminDto;
import eventify.api_spring.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Object[]> pegarConversaoCadastros() {
        Query query = entityManager.createNativeQuery(String.format("CALL sp_kpi_conversao_de_visitantes(6)"));
        return query.getResultList();
    }

    public List<Object[]> pegarPrecisaoFormulario() {
        Query query = entityManager.createNativeQuery(String.format("CALL sp_kpi_precisao_do_formulario(6)"));
        return query.getResultList();
    }

    public List<Object[]> pegarConversaoReservas() {
        Query query = entityManager.createNativeQuery(String.format("CALL sp_kpi_conversao_de_reservas(6)"));
        return query.getResultList();
    }

    public List<Object[]> pegarChurn() {
        Query query = entityManager.createNativeQuery(String.format("CALL sp_churn(6)"));
        return query.getResultList();
    }

    public List<Object[]> pegarRetencaoBuffets() {
        Query query = entityManager.createNativeQuery(String.format("CALL sp_retencao_buffets_retidos(6)"));
        return query.getResultList();
    }

    public List<Object[]> pegarRetencaoFormulario() {
        Query query = entityManager.createNativeQuery(String.format("CALL sp_retencao_formularios_retidos(6)"));
        return query.getResultList();
    }

    public List<Object[]> pegarRetencaoUsuarios() {
        Query query = entityManager.createNativeQuery(String.format("CALL sp_retencao_usuarios_retidos(6)"));
        return query.getResultList();
    }

    public List<UsuarioAdminDto> pegarListaUsuarios() {
        return usuarioRepository.findAllUsuarioLista();
    }

    public List<Object[]> pegarUsuariosBanidos() {
        Query query = entityManager.createNativeQuery(String.format("select nome, tipo_usuario, cpf from usuario where is_banido = 1 order by data_criacao;"));
        return query.getResultList();
    }

    public List<Object[]> pegarListaEndereco() {
        Query query = entityManager.createNativeQuery(String.format("select * from endereco where is_validado = 0;"));
        return query.getResultList();
    }

}
