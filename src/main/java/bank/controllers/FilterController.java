package bank.controllers;

import bank.domain.dtos.UserDto;
import bank.domain.entities.Email;
import bank.domain.entities.Phone;
import bank.domain.entities.User;
import bank.service.FilterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/filtration")
@RequiredArgsConstructor
public class FilterController {
    private final FilterService filterService;
    private static final Function<User, UserDto> MAP_TO_DTO_FUNCTION = u -> new UserDto(u.getFullName(), u.getBirthDate(), u.getEmails().stream().map(Email::getEmailText).collect(Collectors.toSet()), u.getPhones().stream().map(Phone::getPhoneNumber).collect(Collectors.toSet()));

    @Operation(summary = "Фильтрация пользователей")
    @GetMapping()
    public Page<UserDto> searchClients(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) LocalDate birthDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return filterService.searchUsers(fullName, email, phone, birthDate, page, size, sortBy).map(MAP_TO_DTO_FUNCTION);
    }
}
