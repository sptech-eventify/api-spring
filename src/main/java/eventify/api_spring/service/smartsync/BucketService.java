package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.buffet.BuffetServico;
import eventify.api_spring.domain.evento.Evento;
import eventify.api_spring.domain.smartsync.Bucket;
import eventify.api_spring.dto.smartsync.BucketCriacaoDto;
import eventify.api_spring.dto.smartsync.BucketDto;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.repository.BucketRepository;
import eventify.api_spring.repository.BuffetServicoRepository;
import eventify.api_spring.repository.EventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService {
    @Autowired
    private BucketRepository bucketRepository;

    @Autowired 
    private BuffetServicoRepository buffetServicoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public List<Bucket> exibirTodosBuckets() {
        List<Bucket> buckets = bucketRepository.findAll();

        if(buckets.isEmpty()){
            throw new NoContentException("Não há buckets cadastrados");
        }

        return buckets;
    }

    public Bucket exibirBucketPorId(Integer id) {
        Bucket bucket = bucketRepository.findById(id).orElseThrow(() -> new NotFoundException("Bucket não encontrado"));

        return bucket;
    }

    public BucketDto criarBucket(BucketCriacaoDto bucket) {
        BuffetServico secao = buffetServicoRepository.findById(bucket.getIdSecao())
            .orElseThrow(() -> new NotFoundException("Seção não encontrada"));
        Evento evento = eventoRepository.findById(bucket.getIdEvento())
            .orElseThrow(() -> new NotFoundException("Evento não encontrado"));

        Bucket bucketCriado = new Bucket();
        bucketCriado.setNome(bucket.getNome());
        bucketCriado.setBuffetServico(secao);
        bucketCriado.setIsVisivel(true);
        bucketCriado.setEvento(evento);

        Bucket bucketSalvo = bucketRepository.save(bucketCriado);

        BucketDto bucketDto = new BucketDto();
        bucketDto.setId(bucketSalvo.getId());
        bucketDto.setNome(bucketSalvo.getNome());
        bucketDto.setIsVisivel(bucketSalvo.getIsVisivel());
        bucketDto.setIdBuffet(bucketSalvo.getBuffetServico().getBuffet().getId());
        bucketDto.setIdEvento(bucketSalvo.getEvento().getId());
        bucketDto.setIdSecao(bucketSalvo.getBuffetServico().getId());

        return bucketDto;
    }

    public BucketDto atualizarBucket(Integer id, BucketCriacaoDto bucket) {
        Bucket bucketBanco = bucketRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Bucket não encontrado"));

        bucketBanco.setNome(bucket.getNome());

        Bucket bucketAtualizado = bucketRepository.save(bucketBanco);

        BucketDto bucketDto = new BucketDto();
        bucketDto.setId(bucketAtualizado.getId());
        bucketDto.setNome(bucketAtualizado.getNome());
        bucketDto.setIsVisivel(bucketAtualizado.getIsVisivel());
        bucketDto.setIdBuffet(bucketAtualizado.getBuffetServico().getBuffet().getId());
        bucketDto.setIdEvento(bucketAtualizado.getEvento().getId());
        bucketDto.setIdSecao(bucketAtualizado.getBuffetServico().getId());

        return bucketDto;
    }

    public void deletarBucket(Integer id) {
        Bucket bucket = bucketRepository.findById(id).orElseThrow(() -> new NotFoundException("Bucket não encontrado"));

        bucket.setIsVisivel(false);

        bucketRepository.save(bucket);
    }

}
