package eventify.api_spring.mapper.utils;

import java.time.LocalDate;

import eventify.api_spring.dto.utils.DataDto;

public class DataMapper {
    public static DataDto toDto(LocalDate data) {
        DataDto dto = new DataDto();
        dto.setAno(data.getYear());
        dto.setMes(data.getMonthValue());
        dto.setDia(data.getDayOfMonth());
        
        return dto;
    }
}