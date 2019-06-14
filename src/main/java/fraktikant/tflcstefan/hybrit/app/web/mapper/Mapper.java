package fraktikant.tflcstefan.hybrit.app.web.mapper;

import java.util.List;

public interface Mapper<E, DTO> {

    DTO toDTO(E entity);
    E toEntity(DTO dto);
    List<DTO> toDTO(List<E> entities);
    List<E> toEntity(List<DTO> dtos);

}
