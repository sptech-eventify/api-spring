package eventify.api_spring.service.Acesso;

import eventify.api_spring.domain.acesso.Acesso;
import eventify.api_spring.domain.acesso.Pagina;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.repository.AcessoRepository;
import eventify.api_spring.repository.PaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private PaginaRepository paginaRepository;

    public void gerarLog(Integer idPagina) {
        Optional<Pagina> pagina = paginaRepository.findById(idPagina);

        if (pagina.isEmpty()) {
            throw new NoContentException("Não há páginas cadastradas na base de dados");
        }

        Acesso acesso = new Acesso(LocalDateTime.now(), pagina.get());
        acessoRepository.save(acesso);
    }
}