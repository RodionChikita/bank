package bank.security.filtration;
import bank.security.domain.entities.User;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpecification {

    public static Specification<User> hasFullNameLike(String fullName) {
        return (root, query, builder) -> fullName == null ? null : builder.like(root.get("fullName"), fullName + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, builder) -> {
            if (email == null) return null;
            var emailJoin = root.join("emails", JoinType.LEFT);
            return builder.equal(emailJoin.get("emailText"), email);
        };
    }

    public static Specification<User> hasPhone(String phone) {
        return (root, query, builder) -> {
            if (phone == null) return null;
            var phoneJoin = root.join("phones", JoinType.LEFT);
            return builder.equal(phoneJoin.get("phoneNumber"), phone);
        };
    }

    public static Specification<User> birthDateAfter(LocalDate birthDate) {
        return (root, query, builder) -> birthDate == null ? null : builder.greaterThan(root.get("birthDate"), birthDate);
    }
}