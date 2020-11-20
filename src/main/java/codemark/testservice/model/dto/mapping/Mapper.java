package codemark.testservice.model.dto.mapping;

import codemark.testservice.model.dto.AbstractDTO;
import codemark.testservice.model.AbstractEntity;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<E extends AbstractEntity, D extends AbstractDTO> {

    E toEntity(D DTO);
    D toDTO(E entity);

    default List<D> toDTO(List<E> entities) {

        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    default List<E> toEntity(List<D> DTO) {

        return DTO.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}