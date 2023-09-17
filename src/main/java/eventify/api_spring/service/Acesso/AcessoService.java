package eventify.api_spring.service.Acesso;

import eventify.api_spring.domain.acesso.Acesso;
import eventify.api_spring.domain.acesso.Pagina;
import eventify.api_spring.repository.AcessoRepository;
import eventify.api_spring.repository.PaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository AcessoRepository;

    @Autowired
    private PaginaRepository PaginaRepository;

    public void gerarLog(Integer idPagina) {
        LocalDateTime dataLog = LocalDateTime.now();
        Optional<Pagina> pagina = PaginaRepository.findById(idPagina);

        if (pagina.isPresent()) {
            Pagina pagina1 = pagina.get();
            Acesso acesso = new Acesso(dataLog, pagina1);
            AcessoRepository.save(acesso);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Página não encontrada");
        }
    }

}