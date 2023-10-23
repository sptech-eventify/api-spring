package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.usuario.NivelAcesso;

public interface NivelAcessoRepository extends JpaRepository<NivelAcesso, Integer> {

}
