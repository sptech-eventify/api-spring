package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.endereco.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
