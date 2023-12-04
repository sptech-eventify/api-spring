package eventify.api_spring.api.controller.smartsync;

import eventify.api_spring.domain.smartsync.Bucket;
import eventify.api_spring.service.smartsync.BucketService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

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
    public ResponseEntity<Bucket> exibirBucketPorId(@PathVariable   Integer id) {
        Bucket bucket = bucketService.exibirBucketPorId(id);

        return ok(bucket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bucket> atualizarBucket(@PathVariable Integer id){
        Bucket bucketAtualizado = bucketService.atualizarBucket(id);

        return ok(bucketAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBucket(@PathVariable Integer id) {
        bucketService.deletarBucket(id);

        return noContent().build();
    }
}