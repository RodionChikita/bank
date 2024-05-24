package bank;

import bank.security.domain.entities.BankAccount;
import bank.security.domain.entities.User;
import bank.security.repositories.UserRepository;
import bank.security.service.TransferService;
import bank.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TransferServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TransferService transferService;

    private User fromUser;
    private User toUser;

    @BeforeEach
    public void setUp() {
        BankAccount fromAccount = new BankAccount();
        fromAccount.setAmount(1000L);

        fromUser = new User();
        fromUser.setUsername("fromUser");
        fromUser.setBankAccount(fromAccount);

        BankAccount toAccount = new BankAccount();
        toAccount.setAmount(500L);

        toUser = new User();
        toUser.setUsername("toUser");
        toUser.setBankAccount(toAccount);
    }

    @Test
    public void transferMoneyShouldThrowExceptionWhenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            transferService.transferMoney("toUser", -10L);
        });
    }

    @Test
    public void transferMoneyShouldThrowExceptionWhenAmountIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            transferService.transferMoney("toUser", 0L);
        });
    }

    @Test
    public void transferMoneyShouldThrowExceptionWhenInsufficientFunds() {
        when(userService.getCurrentUser()).thenReturn(fromUser);
        when(userService.getByUsername("toUser")).thenReturn(toUser);

        assertThrows(IllegalArgumentException.class, () -> {
            transferService.transferMoney("toUser", 2000L);
        });
    }

    @Test
    public void transferMoneyShouldTransferFundsWhenSufficientFunds() {
        when(userService.getCurrentUser()).thenReturn(fromUser);
        when(userService.getByUsername("toUser")).thenReturn(toUser);

        transferService.transferMoney("toUser", 200L);

        verify(userService, times(1)).save(fromUser);
        verify(userService, times(1)).save(toUser);

        assertEquals(800L, fromUser.getBankAccount().getAmount());
        assertEquals(700L, toUser.getBankAccount().getAmount());
    }

    @Test
    public void transferMoneyShouldThrowExceptionWhenRepositoryFails() {
        when(userService.getCurrentUser()).thenReturn(fromUser);
        when(userService.getByUsername("toUser")).thenReturn(toUser);

        doThrow(new DataAccessException("...") {
        }).when(userService).save(any(User.class));

        assertThrows(DataAccessException.class, () -> {
            transferService.transferMoney("toUser", 200L);
        });

        verify(userService, times(1)).save(fromUser);
        verify(userService, never()).save(toUser);
    }
}
