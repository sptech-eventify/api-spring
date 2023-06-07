package eventify.api_spring.dto;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Usuario;

import java.time.LocalDate;

public record EventoCriacaoDto(LocalDate data, Double preco, Boolean isFormularioDinamico, Buffet buffet, Usuario contratante) {
}
