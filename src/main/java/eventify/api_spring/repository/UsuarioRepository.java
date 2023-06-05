package eventify.api_spring.repository;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.usuario.UsuarioAdminDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eventify.api_spring.dto.BuffetInfoDto;
import eventify.api_spring.dto.usuario.BuffetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

// Interface que herda de JpaRepository e recebe a classe que será mapeada e o tipo do ID
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
