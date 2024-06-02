package dio.api.domain.services;

import dio.api.domain.models.Transaction;
import dio.api.domain.models.User;
import dio.api.domain.repositories.TransactionRepository;
import dio.api.dtos.transaction.CreateTransactionRequestDTO;
import dio.api.dtos.transaction.CreateTransactionResponseDTO;
import dio.api.dtos.transaction.FindAllBySenderIdRequestDTO;
import dio.api.dtos.transaction.FindAllBySenderIdResponseDTO;
import dio.api.dtos.user.FindUserByDocumentRequestDTO;
import dio.api.dtos.user.FindUserByDocumentResponseDTO;
import dio.api.dtos.user.FindUserByIdResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public FindAllBySenderIdResponseDTO findAllBySenderId(FindAllBySenderIdRequestDTO sender) throws IllegalArgumentException {
        User user = this.userService.findById(sender.senderId());

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<Transaction> transactions = this.transactionRepository.findAllBySenderId(sender.senderId());

        if (transactions.isEmpty()) {
            return null;
        }

        return new FindAllBySenderIdResponseDTO(transactions);
    }

    public CreateTransactionResponseDTO create(CreateTransactionRequestDTO body) throws IllegalArgumentException {
        User sender = this.userService.findById(body.senderId());

        if (sender == null) {
            throw new IllegalArgumentException("Sender not found");
        }

        if (body.amount().compareTo(sender.getBalance()) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        FindUserByDocumentRequestDTO userByDocumentRequest = new FindUserByDocumentRequestDTO(body.receiverDocument());
        FindUserByDocumentResponseDTO receiverId = this.userService.findByDocument(userByDocumentRequest);
        User receiver = this.userService.findById(receiverId.id());

        if (receiver == null) {
            throw new IllegalArgumentException("Receiver not found");
        }

        sender.setBalance(sender.getBalance().subtract(body.amount()));
        receiver.setBalance(receiver.getBalance().add(body.amount()));

        Transaction transactionData = new Transaction();
        transactionData.setReceiverId(receiver.getId());
        transactionData.setSenderId(body.senderId());
        transactionData.setAmount(body.amount());

        Transaction newTransacton = this.transactionRepository.save(transactionData);
        return new CreateTransactionResponseDTO(newTransacton);
    }
}
