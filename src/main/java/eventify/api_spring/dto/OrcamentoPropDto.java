package eventify.api_spring.dto;

import java.time.LocalDate;

public record OrcamentoPropDto(Integer id, String nomeContratante, LocalDate data, Double preco, String status) {
}
