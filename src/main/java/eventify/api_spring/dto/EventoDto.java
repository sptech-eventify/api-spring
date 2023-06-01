package eventify.api_spring.dto;

import java.time.LocalDate;

public record EventoDto(Integer id, String nome, LocalDate data, Double preco, Double nota, String descricao, String caminho, String status) {
}

