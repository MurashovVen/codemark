package codemark.testservice.model.dto.mapping;

import codemark.testservice.model.dto.UserDTO;
import codemark.testservice.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<UserEntity, UserDTO> {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public UserEntity toEntity(UserDTO DTO) {

        return new UserEntity(
                DTO.getUsername(),
                DTO.getLogin(),
                DTO.getPassword(),
                roleMapper.toEntity(DTO.getRoles())
        );
    }

    @Override
    public UserDTO toDTO(UserEntity entity) {

        return new UserDTO(
                entity.getUsername(),
                entity.getLogin(),
                entity.getPassword(),
                roleMapper.toDTO(entity.getRoles())
        );
    }

    public UserDTO toDTOWithoutRoles(UserEntity entity) {
        return new UserDTO(
                entity.getUsername(),
                entity.getLogin(),
                entity.getPassword(),
                null
        );
    }

    @Override
    public List<UserDTO> toDTO(List<UserEntity> entities) {
        return entities.stream()
                .map(this::toDTOWithoutRoles)
                .collect(Collectors.toList());
    }
}
