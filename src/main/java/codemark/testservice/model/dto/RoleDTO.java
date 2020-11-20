package codemark.testservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
public class RoleDTO extends AbstractDTO{
    private int id;
    private String name;
}
