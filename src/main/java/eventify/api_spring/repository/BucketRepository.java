package eventify.api_spring.repository;

import eventify.api_spring.domain.smartsync.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Integer> {

}
