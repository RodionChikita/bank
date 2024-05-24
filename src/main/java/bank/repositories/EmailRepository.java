package bank.repositories;

import bank.domain.entities.Email;
import bank.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    boolean existsByEmailText(String emailText);
    Optional<Email> findByEmailTextAndUser(String email, User user);
    @Modifying
    @Query(value = """
            DELETE FROM emails
            WHERE emails.id = :id
            """,
            nativeQuery = true)
    void deleteById(Long id);
}
