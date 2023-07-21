package eventify.api_spring.dto.evento;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.usuario.Usuario;

import java.time.LocalDate;

public record EventoCriacaoDto(LocalDate data, Double preco, Boolean isFormularioDinamico, Buffet buffet, Usuario contratante) {
}
