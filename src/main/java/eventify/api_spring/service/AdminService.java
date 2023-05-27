package eventify.api_spring.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private EntityManager entityManager;

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

    public List<Object[]> pegarListaUsuarios() {
        Query query = entityManager.createNativeQuery(String.format("SELECT\n" +
                "  usuario.nome,\n" +
                "  usuario.tipo_usuario,\n" +
                "  usuario.is_banido,\n" +
                "  usuario.is_ativo,\n" +
                "  usuario.cpf,\n" +
                "  usuario.data_criacao,\n" +
                "  COUNT(CASE WHEN Evento.status = 6 THEN 1 END) AS quantidade_eventos_status_6,\n" +
                "  COUNT(Evento.id) AS quantidade_total_eventos\n" +
                "FROM\n" +
                "  Usuario\n" +
                "LEFT JOIN\n" +
                "  Evento ON Usuario.id = Evento.id_contratante\n" +
                "GROUP BY\n" +
                "  Usuario.id,\n" +
                "  Usuario.nome;"));
        return query.getResultList();
    }

    public List<Object[]> pegarUsuariosBanidos() {
        Query query = entityManager.createNativeQuery(String.format("select nome, tipo_usuario, cpf from usuario where is_banido = 1;"));
        return query.getResultList();
    }

}
