package eventify.api_spring.repository;

import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.usuario.UsuarioAdminDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT new eventify.api_spring.dto.usuario.UsuarioAdminDto(u.nome," +
            "u.tipoUsuario," +
            "u.isBanido," +
            "u.isAtivo," +
            "u.cpf," +
            "u.dataCriacao," +
            "COUNT(CASE WHEN e.status = '6' THEN 1 END)," +
            "COUNT(e.id))" +
            "FROM Usuario u " +
            "JOIN Evento e on e.contratante = u " +
            "GROUP BY u.nome")
    List<UsuarioAdminDto> findAllUsuarioLista();
}
