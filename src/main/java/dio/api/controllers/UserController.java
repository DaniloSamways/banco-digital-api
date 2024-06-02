package dio.api.controllers;

import dio.api.domain.models.User;
import dio.api.domain.services.UserService;
import dio.api.dtos.response.ErrorResponseDTO;
import dio.api.dtos.user.CreateUserRequestDTO;
import dio.api.dtos.user.CreateUserResponseDTO;
import dio.api.dtos.user.FindUserByDocumentRequestDTO;
import dio.api.dtos.user.FindUserByDocumentResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateUserRequestDTO body) {
        try {
            CreateUserResponseDTO user = this.userService.create(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException error) {
            List<String> errors = Collections.singletonList(error.getMessage());
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(errors);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/{document}")
    public ResponseEntity<Object> findByDocument(@PathVariable String document) {
        try {
            FindUserByDocumentResponseDTO user = this.userService.findByDocument(new FindUserByDocumentRequestDTO(document));
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (IllegalArgumentException error) {
            List<String> errors = Collections.singletonList(error.getMessage());
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(errors);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
