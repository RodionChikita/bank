package bank.security.repositories;

import bank.security.domain.entities.Phone;
import bank.security.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = """
            DELETE FROM phones
            WHERE phones.id = :id
            """,
            nativeQuery = true)
    void deleteById(Long id);

    Optional<Phone> findByPhoneNumberAndUser(String phone, User user);
}
