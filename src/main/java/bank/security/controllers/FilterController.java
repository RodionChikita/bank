package bank.security.controllers;

import bank.security.domain.entities.User;
import bank.security.service.FilterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class FilterController {
    private final FilterService filterService;
    @Operation(summary = "Фильтрация пользователей")
    @GetMapping("/clients")
    public Page<User> searchClients(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) LocalDate birthDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return filterService.searchUsers(fullName, email, phone, birthDate, page, size, sortBy);
    }
}
