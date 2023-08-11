package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.FaixaEtaria;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.repository.FaixaEtariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaixaEtariaService {

    @Autowired
    private FaixaEtariaRepository faixaEtariaRepository;

    public List<FaixaEtaria> faixasEtarias() {
        List<FaixaEtaria> faixasEtarias = faixaEtariaRepository.findAllByOrderByIdAsc();

        if (faixasEtarias.isEmpty()) {
            throw new NoContentException("Não há faixas etárias cadastradas");
        }

        return faixasEtarias;
    }
}
