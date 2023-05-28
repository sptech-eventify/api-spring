package eventify.api_spring.service;

import eventify.api_spring.api.assets.Fila;
import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        Query query = entityManager.createNativeQuery(String.format("select nome, tipo_usuario, cpf from usuario where is_banido = 1 order by data_criacao;"));
        return query.getResultList();
    }

    public List<Endereco> pegarListaEndereco() {
        Query query = entityManager.createNativeQuery(String.format("select * from endereco where is_validado = 0;"));
        List<Object[]> lista = query.getResultList();
        Fila<Endereco> enderecos = new Fila<>(lista.size());

        for (Object[] o : lista) {
            System.out.println(o[0]);
            Integer id = (Integer) o[0];

            System.out.println(o[1]);
            boolean isValidado = ((Byte) o[1]) != 0;

            System.out.println(o[2]);
            String logradouro = (String) o[2];

            System.out.println(o[3]);
            Integer numero = (Integer) o[3];

            System.out.println(o[4]);
            String bairro = (String) o[4];

            System.out.println(o[5]);
            String cidade = (String) o[5];

            System.out.println(o[6]);
            String uf = (String) o[6];

            System.out.println(o[7]);
            String cep = (String) o[7];

            System.out.println(o[8]);
            BigDecimal latitudeDecimal = (BigDecimal) o[8];
            Double latitude = latitudeDecimal.doubleValue();

            System.out.println(o[9]);
            BigDecimal longitudeDecimal = (BigDecimal) o[9];
            Double longitude = longitudeDecimal.doubleValue();

            System.out.println(o[10]);
            LocalDate dataCriacao = (LocalDate) o[10]; // Preencha com o valor correspondente do objeto

            Endereco endereco = new Endereco(id, isValidado, logradouro, numero, bairro, cidade, uf, cep, latitude, longitude, dataCriacao);
            enderecos.insert(endereco);
        }

        return enderecos.getValores();
    }


}
