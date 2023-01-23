package africa.semicolon.unicoin.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@RequiredArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="This field is required")
    private String firstName;
    @NotBlank(message="This field is required")
    private String lastName;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean isDisabled = true;
    @NotBlank(message="This field is required")
    @Email(message="This field requires a valid email address")
    private String emailAddress;
    public User(String emailAddress, String firstName, String lastName, String password, UserRole userRole){
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRole = userRole;
    }
}
