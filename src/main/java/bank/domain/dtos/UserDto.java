package bank.domain.dtos;

import bank.domain.entities.Email;
import bank.domain.entities.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String fullName;

    private Date birthDate;

    private Set<String> emails;

    private Set<String> phones;
}
