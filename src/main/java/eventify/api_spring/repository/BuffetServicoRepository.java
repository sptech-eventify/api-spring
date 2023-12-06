package eventify.api_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.buffet.BuffetServico;

public interface BuffetServicoRepository extends JpaRepository<BuffetServico, Integer> {
    List<BuffetServico> findByBuffetId(Integer id);
}
