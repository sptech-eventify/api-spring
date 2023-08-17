package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.pagamento.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}