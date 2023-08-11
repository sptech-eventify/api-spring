package eventify.api_spring.dto.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetencaoDto {
    private Object mes;
    private Object totalRetidos;
}
