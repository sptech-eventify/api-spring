package eventify.api_spring.repository;

import eventify.api_spring.domain.FaixaEtaria;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaixaEtariaRepository extends JpaRepository<FaixaEtaria, Integer> {
}
