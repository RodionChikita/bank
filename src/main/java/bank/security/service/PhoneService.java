package bank.security.service;

import bank.security.domain.entities.Email;
import bank.security.domain.entities.Phone;
import bank.security.domain.entities.User;
import bank.security.repositories.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhoneService {
    private final UserService userService;
    private final PhoneRepository phoneRepository;
    public void addNewPhone(String phone) {
        if (phoneRepository.existsByPhoneNumber(phone)) {
            throw new RuntimeException("Пользователь с таким телефоном уже существует");
        }
        User user = userService.getCurrentUser();
        Phone newPhone = Phone.builder()
                .user(user)
                .phoneNumber(phone)
                .build();
        phoneRepository.save(newPhone);
    }

    public void deletePhone(String phone) {
        User user = userService.getCurrentUser();
        if (user.getEmails().size() == 1){
            throw new RuntimeException("Нельзя удалить последний телефон");
        }
        Optional<Phone> phoneToDelete = phoneRepository.findByPhoneNumberAndUser(phone, user);
        if(phoneToDelete.isEmpty()){
            throw new RuntimeException("Такой email отсутствует у пользователя");
        }
        phoneRepository.deleteById(phoneToDelete.get().getId());
    }

    public void updatePhone(String oldPhone, String newPhone) {
        User user = userService.getCurrentUser();
        Optional<Phone> phoneToUpdate = phoneRepository.findByPhoneNumberAndUser(oldPhone, user);
        if(phoneToUpdate.isEmpty()){
            throw new RuntimeException("Такой email отсутствует у пользователя");
        }
        phoneToUpdate.get().setPhoneNumber(newPhone);
        phoneRepository.save(phoneToUpdate.get());
    }
}