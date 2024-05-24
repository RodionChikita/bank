package bank.security.service;

import bank.security.domain.entities.BankAccount;
import bank.security.repositories.BankAccountRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    @PostConstruct
    public void onStartup() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable updateTask = () -> {
            updateBalances();
        };
        scheduler.scheduleAtFixedRate(updateTask, 0, 1, TimeUnit.MINUTES);
    }

    public void updateBalances() {
        for (BankAccount bankAccount : bankAccountRepository.findAll()) {
            Long newAmount = (long) (bankAccount.getAmount() * 1.05);
            if (newAmount < bankAccount.getMaxAmount()) {
                bankAccount.setAmount(newAmount);
            } else {
                bankAccount.setAmount(bankAccount.getMaxAmount());
            }
            bankAccountRepository.save(bankAccount);
        }
    }
}
