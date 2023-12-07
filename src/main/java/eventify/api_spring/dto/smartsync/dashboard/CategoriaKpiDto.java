package eventify.api_spring.dto.smartsync.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaKpiDto {
    private String categoria;
    private Integer quantidade;
}
