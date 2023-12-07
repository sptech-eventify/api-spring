package eventify.api_spring.dto.smartsync.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDto {
    private Integer ano;
    private String mes;
    private Long entraram;
    private Long sairam;
}