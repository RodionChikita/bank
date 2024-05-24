package bank.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Имя пользователя", example = "Jondoe")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;

    @Schema(description = "ФИО", example = "Ivanov Ivan Ivanovich")
    @Size(min = 5, max = 255, message = "ФИО должен содержать от 5 до 255 символов")
    @NotBlank(message = "ФИО не может быть пустыми")
    private String fullName;

    @Schema(description = "Дата рождения", example = "2005-10-16")
    @NotBlank(message = "Дата рождение не может быть пустой")
    private Date birthDate;

    @Schema(description = "Номер телефона", example = "84342214550")
    @NotBlank(message = "Телефон не может быть пустыми")
    private String phone;

    @Schema(description = "Количество денег на счете", example = "100000")
    @NotBlank(message = "Количество денег на счете не может быть пустыми")
    private Long amount;
}