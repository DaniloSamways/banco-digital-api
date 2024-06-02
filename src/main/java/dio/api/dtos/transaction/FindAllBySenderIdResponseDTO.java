package dio.api.dtos.transaction;

import dio.api.domain.models.Transaction;
import dio.api.domain.models.User;

import java.util.List;

public record FindAllBySenderIdResponseDTO(List<Transaction> transactions) {
}
