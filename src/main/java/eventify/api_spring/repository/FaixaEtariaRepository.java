package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eventify.api_spring.domain.buffet.FaixaEtaria;

import java.util.List;

@Repository
public interface FaixaEtariaRepository extends JpaRepository<FaixaEtaria, Integer> {

    List<FaixaEtaria> findAllByOrderByIdAsc();
}
