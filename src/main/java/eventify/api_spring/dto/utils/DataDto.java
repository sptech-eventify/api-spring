package eventify.api_spring.dto.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataDto {
    private int ano;
    private int mes;
    private int dia;
}