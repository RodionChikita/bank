package bank.security.controllers;

import bank.security.service.EmailService;
import bank.security.service.PhoneService;
import bank.security.service.TransferService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final EmailService emailService;
    private final PhoneService phoneService;
    private final TransferService transferService;
    @GetMapping("/addEmail/{email}")
    public void addNewEmail(@PathVariable String email){
        emailService.addNewEmail(email);
    }
    @PostMapping("/addPhone/{phone}")
    public void addNewPhone(@PathVariable String phone){
        phoneService.addNewPhone(phone);
    }
    @DeleteMapping("/deletePhone/{phone}")
    public void deletePhone(@PathVariable String phone){
        phoneService.deletePhone(phone);
    }
    @DeleteMapping("/deleteEmail/{email}")
    public void deleteEmail(@PathVariable String email){
        emailService.deleteEmail(email);
    }
    @DeleteMapping("/updatePhone")
    public void updatePhone(@RequestParam String oldPhone, @RequestParam String newPhone){
        phoneService.updatePhone(oldPhone, newPhone);
    }
    @DeleteMapping("/updateEmail")
    public void updateEmail(@RequestParam String oldEmail, @RequestParam String newEmail){
        emailService.updateEmail(oldEmail, newEmail);
    }
    @GetMapping("/transferMoney")
    public void transferMoney(@RequestParam String toUserName, @RequestParam Long amount){transferService.transferMoney(toUserName, amount);}
}
