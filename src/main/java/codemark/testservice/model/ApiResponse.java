package codemark.testservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse implements Serializable {
    private boolean status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ApiResponse(boolean status) {
        this.status = status;
    }
}
