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

}
