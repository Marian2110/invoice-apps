package exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponse(String internalCode, String message, List<FieldError> fieldErrors) {
}
