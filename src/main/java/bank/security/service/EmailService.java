package bank.security.service;

import bank.security.domain.entities.Email;
import bank.security.domain.entities.User;
import bank.security.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JwtService jwtService;
    private final UserService userService;
    private final EmailRepository emailRepository;
    public void addNewEmail(String email) {
        if (emailRepository.existsByEmailText(email)) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        User user = userService.getCurrentUser();
        Email newEmail = Email.builder()
                .user(user)
                .emailText(email)
                .build();
        emailRepository.save(newEmail);
    }

    public void deleteEmail(String email) {
        User user = userService.getCurrentUser();
        if (user.getEmails().size() == 1){
            throw new RuntimeException("Нельзя удалить последний email");
        }
        Optional<Email> emailToDelete = emailRepository.findByEmailTextAndUser(email, user);
        if(emailToDelete.isEmpty()){
            throw new RuntimeException("Такой email отсутствует у пользователя");
        }
        emailRepository.deleteById(emailToDelete.get().getId());
    }

    public void updateEmail(String oldEmail, String newEmail) {
        User user = userService.getCurrentUser();
        Optional<Email> emailToUpdate = emailRepository.findByEmailTextAndUser(oldEmail, user);
        if(emailToUpdate.isEmpty()){
            throw new RuntimeException("Такой email отсутствует у пользователя");
        }
        emailToUpdate.get().setEmailText(newEmail);
        emailRepository.save(emailToUpdate.get());
    }
}
