package africa.semicolon.unicoin.registration.dtos;

import africa.semicolon.unicoin.registration.resetToken.ResetPasswordToken;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConfirmTokenRequest {
    @NotNull
    private String token;
    @NotNull
    private String email;
}
