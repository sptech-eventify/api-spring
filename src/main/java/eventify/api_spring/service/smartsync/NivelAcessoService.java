package eventify.api_spring.service.smartsync;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.domain.usuario.NivelAcesso;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.repository.NivelAcessoRepository;

@Service
public class NivelAcessoService {
    @Autowired
    private NivelAcessoRepository nivelAcessoRepository;

    public NivelAcesso findById(Integer id) {
        Optional<NivelAcesso> nivelAcesso = nivelAcessoRepository.findById(id);

        if (nivelAcesso.isEmpty()) {
            throw new NotFoundException("Nivel de acesso não encontrado");
        }

        return nivelAcesso.get();
    }
}
