package eventify.api_spring.dto.smartsync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BucketDto {
    private Integer id;
    private String nome;
    private Boolean isVisivel;
    private Integer idBuffet;
    private Integer idEvento;
    private Integer idSecao;
}
