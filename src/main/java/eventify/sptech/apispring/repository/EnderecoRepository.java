package eventify.sptech.apispring.repository;

import eventify.sptech.apispring.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
