package bank.service;

import bank.filtration.UserSpecification;
import bank.repositories.UserRepository;
import bank.domain.entities.User;
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
        //return users.map(UserMapper.INSTANCE::userToUserDto);
    }
}
