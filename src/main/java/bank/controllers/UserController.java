package bank.controllers;

import bank.service.EmailService;
import bank.service.PhoneService;
import bank.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final EmailService emailService;
    private final PhoneService phoneService;
    private final TransferService transferService;
    @Operation(summary = "Добавление email")
    @GetMapping("/addEmail/{email}")
    public void addNewEmail(@PathVariable String email){
        emailService.addNewEmail(email);
    }
    @Operation(summary = "Добавление номера телефона")
    @PostMapping("/addPhone/{phone}")
    public void addNewPhone(@PathVariable String phone){
        phoneService.addNewPhone(phone);
    }
    @Operation(summary = "Удаление номера телефона")
    @DeleteMapping("/deletePhone/{phone}")
    public void deletePhone(@PathVariable String phone){
        phoneService.deletePhone(phone);
    }
    @Operation(summary = "Удаление email")
    @DeleteMapping("/deleteEmail/{email}")
    public void deleteEmail(@PathVariable String email){
        emailService.deleteEmail(email);
    }
    @Operation(summary = "Обновление номера телефона")
    @DeleteMapping("/updatePhone")
    public void updatePhone(@RequestParam String oldPhone, @RequestParam String newPhone){
        phoneService.updatePhone(oldPhone, newPhone);
    }
    @Operation(summary = "Обновление email")
    @DeleteMapping("/updateEmail")
    public void updateEmail(@RequestParam String oldEmail, @RequestParam String newEmail){
        emailService.updateEmail(oldEmail, newEmail);
    }
    @GetMapping("/transferMoney")
    public void transferMoney(@RequestParam String toUserName, @RequestParam Long amount){transferService.transferMoney(toUserName, amount);}
}
