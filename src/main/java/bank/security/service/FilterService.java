package bank.security.service;

import bank.security.domain.entities.User;
import bank.security.filtration.UserSpecification;
import bank.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@RequiredArgsConstructor
public class FilterService {
    private final UserRepository userRepository;
    public Page<User> searchUsers(String fullName, String email, String phone, LocalDate birthDate, int page, int size, String sortBy) {
        Specification<User> spec = Specification.where(UserSpecification.hasFullNameLike(fullName))
                .and(UserSpecification.hasEmail(email))
                .and(UserSpecification.hasPhone(phone))
                .and(UserSpecification.birthDateAfter(birthDate));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRepository.findAll(spec, pageable);
    }
}
