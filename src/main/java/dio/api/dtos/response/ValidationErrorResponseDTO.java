package dio.api.dtos.response;

import java.util.List;

public record ValidationErrorResponseDTO(List<String> errors) {
}
