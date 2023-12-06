package eventify.api_spring.api.controller.smartsync;

import eventify.api_spring.domain.smartsync.Bucket;
import eventify.api_spring.dto.smartsync.BucketCriacaoDto;
import eventify.api_spring.dto.smartsync.BucketDto;
import eventify.api_spring.service.smartsync.BucketService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

import java.net.URI;
import java.util.List;

@SecurityRequirement(name = "requiredAuth")
@RestController
@RequestMapping("/buckets")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = {"Access-Control-Expose-Headers", "Access-Token", "Uid"})
public class BucketController {
    @Autowired
    private BucketService bucketService;

    @GetMapping
    public ResponseEntity<List<Bucket>> exibirTodosBuckets() {
        List<Bucket> buckets = bucketService.exibirTodosBuckets();

        return ok(buckets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bucket> exibirBucketPorId(@PathVariable Integer id) {
        Bucket bucket = bucketService.exibirBucketPorId(id);

        return ok(bucket);
    }

    @PostMapping
    public ResponseEntity<BucketDto> criarBucket(@RequestBody BucketCriacaoDto bucket) {
        BucketDto bucketCriado = bucketService.criarBucket(bucket);
        URI location = URI.create(String.format("/buckets/%s", bucketCriado.getId()));

        return created(location).body(bucketCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BucketDto> atualizarBucket(@PathVariable Integer id, @RequestBody BucketCriacaoDto bucket){
        BucketDto bucketAtualizado = bucketService.atualizarBucket(id, bucket);

        return ok(bucketAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBucket(@PathVariable Integer id) {
        bucketService.deletarBucket(id);

        return noContent().build();
    }
}