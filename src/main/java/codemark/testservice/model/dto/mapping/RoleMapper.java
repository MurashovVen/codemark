package codemark.testservice.model.dto.mapping;

import codemark.testservice.model.dto.RoleDTO;
import codemark.testservice.model.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements Mapper<RoleEntity, RoleDTO>{

    @Override
    public RoleEntity toEntity(RoleDTO DTO) {

        return new RoleEntity(
                DTO.getId(),
                DTO.getName()
        );
    }

    @Override
    public RoleDTO toDTO(RoleEntity entity) {

        return new RoleDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
