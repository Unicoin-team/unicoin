package africa.semicolon.unicoin.registration.dtos;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
