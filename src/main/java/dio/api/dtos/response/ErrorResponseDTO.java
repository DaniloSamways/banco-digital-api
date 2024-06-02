package dio.api.dtos.response;

import java.util.List;

public record ErrorResponseDTO(List<String> errors) {
}
