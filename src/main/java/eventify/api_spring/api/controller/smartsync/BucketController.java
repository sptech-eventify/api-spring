package eventify.api_spring.api.controller.smartsync;

import eventify.api_spring.domain.smartsync.Bucket;
import eventify.api_spring.service.smartsync.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buckets")
public class BucketController {
    @Autowired
    private BucketService bucketService;

    @GetMapping
    public ResponseEntity<List<Bucket>> exibirTodosBuckets() {
        List<Bucket> buckets = bucketService.exibirTodosBuckets();

        return ResponseEntity.ok(buckets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bucket> exibirBucketPorId(@PathVariable Integer id) {
        Bucket bucket = bucketService.exibirBucketPorId(id);

        return ResponseEntity.ok(bucket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bucket> atualizarBucket(@PathVariable Integer id){
        Bucket bucketAtualizado = bucketService.atualizarBucket(id);

        return ResponseEntity.ok(bucketAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBucket(@PathVariable Integer id) {
        bucketService.deletarBucket(id);

        return ResponseEntity.noContent().build();
    }
}
