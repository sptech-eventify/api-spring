package eventify.api_spring.service;

import eventify.api_spring.domain.Servico;
import eventify.api_spring.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public Servico salvar(Servico s) {
        return this.servicoRepository.save(s);
    }

    public List<Servico> listaServico() {
        return this.servicoRepository.findAll();
    }
}
