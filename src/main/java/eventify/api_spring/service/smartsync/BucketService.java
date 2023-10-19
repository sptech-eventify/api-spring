package eventify.api_spring.service.smartsync;

import eventify.api_spring.domain.smartsync.Bucket;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.repository.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService {
    @Autowired
    private BucketRepository bucketRepository;

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

    public Bucket criarBucket(Bucket bucket) {
        return bucketRepository.save(bucket);
    }

    public Bucket atualizarBucket(Integer id){
        Bucket bucket = bucketRepository.findById(id).orElseThrow(() -> new NotFoundException("Bucket não encontrado"));

        return bucketRepository.save(bucket);
    }

    public void deletarBucket(Integer id) {
        Bucket bucket = bucketRepository.findById(id).orElseThrow(() -> new NotFoundException("Bucket não encontrado"));

        bucket.setIsVisivel(false);

        bucketRepository.save(bucket);
    }

}
