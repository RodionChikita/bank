package bank.service;

import bank.domain.dtos.JwtAuthenticationResponse;
import bank.domain.dtos.SignInRequest;
import bank.domain.dtos.SignUpRequest;
import bank.domain.entities.*;
import bank.domain.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .birthDate(request.getBirthDate())
                .fullName(request.getFullName())
                .build();
        Set<Email> emails = new HashSet<>();
        Email email = Email.builder()
                .user(user)
                .emailText(request.getEmail())
                .build();
        emails.add(email);
        Set<Phone> phones = new HashSet<>();
        Phone phone = Phone.builder()
                .user(user)
                .phoneNumber(request.getPhone())
                .build();
        phones.add(phone);
        BankAccount bankAccount = BankAccount.builder()
                .user(user)
                .amount(request.getAmount())
                .maxAmount((long) (request.getAmount()*3.07))
                .build();
        user.setEmails(emails);
        user.setBankAccount(bankAccount);
        user.setPhones(phones);
        userService.create(user);

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }


    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
