package codemark.testservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDTO extends AbstractDTO{
    private String username;
    private String login;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RoleDTO> roles;
}
