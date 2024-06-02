package dio.api.domain.services;

import dio.api.domain.models.User;
import dio.api.domain.repositories.UserRepository;
import dio.api.dtos.user.CreateUserRequestDTO;
import dio.api.dtos.user.CreateUserResponseDTO;
import dio.api.dtos.user.FindUserByDocumentRequestDTO;
import dio.api.dtos.user.FindUserByDocumentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public CreateUserResponseDTO create(CreateUserRequestDTO user) throws IllegalArgumentException {
        User documentAlreadyExists = this.userRepository.findByDocument(user.document());
        User emailAlreadyExists = this.userRepository.findByEmail(user.email());

        if (documentAlreadyExists != null) {
            throw new IllegalArgumentException("Document already registered");
        }

        if (emailAlreadyExists != null) {
            throw new IllegalArgumentException("Email already registered");
        }

        User userData = new User();
        userData.setBalance(BigDecimal.ZERO);
        userData.setDocument(user.document());
        userData.setEmail(user.email());
        userData.setPassword(user.password());
        userData.setFirstName(user.firstName());
        userData.setLastName(user.lastName());

        User newUser = this.userRepository.save(userData);
        return new CreateUserResponseDTO(newUser);
    }

    public FindUserByDocumentResponseDTO findByDocument(FindUserByDocumentRequestDTO user) throws IllegalArgumentException {
        User userData = this.userRepository.findByDocument(user.document());

        if (userData == null) {
            throw new IllegalArgumentException("User not found");
        }

        User userResponse = this.userRepository.findByDocument(user.document());
        return new FindUserByDocumentResponseDTO(
                userResponse.getId(),
                userResponse.getDocument(),
                userResponse.getFirstName(),
                userResponse.getLastName()
        );
    }

    public User findById(String id) {
        Optional<User> user = this.userRepository.findById(id);

        return user.orElse(null);
    }

}
