package bank.service;

import bank.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TransferService {
    private final UserService userService;
    @Transactional
    public synchronized void transferMoney(String toUserName, Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }

        User fromUser = userService.getCurrentUser();
        User toUser = userService.getByUsername(toUserName);

        if (fromUser.getBankAccount().getAmount() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        fromUser.getBankAccount().setAmount(fromUser.getBankAccount().getAmount() - amount);
        toUser.getBankAccount().setAmount(toUser.getBankAccount().getAmount() + amount);
        userService.save(fromUser);
        userService.save(toUser);
    }
}
