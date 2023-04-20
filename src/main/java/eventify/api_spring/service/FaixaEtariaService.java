package eventify.api_spring.service;

import eventify.api_spring.domain.FaixaEtaria;
import eventify.api_spring.repository.FaixaEtariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaixaEtariaService {

    @Autowired
    private FaixaEtariaRepository faixaEtariaRepository;

    public void criarFaixaEtaria(FaixaEtaria f) {
        faixaEtariaRepository.save(f);
    }

    public List<FaixaEtaria> exibirFaixaEtaria() {
        return faixaEtariaRepository.findAll();
    }

}
