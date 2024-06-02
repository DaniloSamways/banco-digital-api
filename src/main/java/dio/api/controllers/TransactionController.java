package dio.api.controllers;

import dio.api.domain.models.Transaction;
import dio.api.domain.services.TransactionService;
import dio.api.dtos.response.ErrorResponseDTO;
import dio.api.dtos.transaction.CreateTransactionRequestDTO;
import dio.api.dtos.transaction.CreateTransactionResponseDTO;
import dio.api.dtos.transaction.FindAllBySenderIdRequestDTO;
import dio.api.dtos.transaction.FindAllBySenderIdResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{senderId}")
    public ResponseEntity<Object> findAllBySenderId(@PathVariable String senderId) {
        try {
            FindAllBySenderIdResponseDTO transactions = this.transactionService.findAllBySenderId(new FindAllBySenderIdRequestDTO(senderId));
            return ResponseEntity.status(HttpStatus.OK).body(transactions);
        } catch (IllegalArgumentException error) {
            List<String> errors = Collections.singletonList(error.getMessage());
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(errors);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateTransactionRequestDTO body) {
        try {
            CreateTransactionResponseDTO transaction = this.transactionService.create(body);
            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        }  catch (IllegalArgumentException error) {
            List<String> errors = Collections.singletonList(error.getMessage());
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(errors);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
