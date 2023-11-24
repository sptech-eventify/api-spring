package eventify.api_spring.service.smartsync;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.domain.smartsync.Acao;
import eventify.api_spring.dto.smartsync.AcaoDto;
import eventify.api_spring.repository.AcaoRepository;

@Service
public class AcaoService {
    @Autowired
    private AcaoRepository acaoRepository;

    public List<AcaoDto> retornarAcoes() {
        List<Acao> acoes = acaoRepository.findAll();

        return acoes.stream().map(acao -> new AcaoDto(acao.getId(), acao.getDescricao())).collect(Collectors.toList());
    }
}
