package dio.api.dtos.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateTransactionRequestDTO(
        @NotBlank(message = "senderId is required")
        String senderId,

        @NotBlank(message = "receiverDocument is required")
        String receiverDocument,

        @NotNull(message = "amount is required")
        @Positive(message = "amount must be greater than 0")
        BigDecimal amount
) {
}
