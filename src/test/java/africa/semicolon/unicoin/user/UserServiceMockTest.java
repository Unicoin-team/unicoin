package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.mockUtils.MockUtils;
import africa.semicolon.unicoin.registration.dtos.ConfirmTokenRequest;
import africa.semicolon.unicoin.registration.dtos.ForgotPasswordRequest;
import africa.semicolon.unicoin.registration.dtos.ResetPasswordRequest;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordToken;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static africa.semicolon.unicoin.mockUtils.MockUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

    public class UserServiceMockTest {

        private final UserService userServiceMock = spy(MockUtils.userService());

        @Test
        void testForgotPassword() throws MessagingException {
            ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
            forgotPasswordRequest.setEmail("mrjesus@gmail.com");

            Optional<User> user = Optional.of(new User());
            doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase(forgotPasswordRequest.getEmail());

            assertEquals("We have sent a reset password link to your email. Please check.", userServiceMock.forgotPassword(forgotPasswordRequest));

        }

        @Test
        void testConfirmResetToken() {
            ConfirmTokenRequest confirmTokenRequest = new ConfirmTokenRequest();
            confirmTokenRequest.setToken("94b199ea-614e-4a16-9f50-f94f5611bae9");

            Optional<ResetPasswordToken> resetPasswordToken = Optional.of(new ResetPasswordToken());
            resetPasswordToken.get().setToken(confirmTokenRequest.getToken());
            resetPasswordToken.get().setCreatedAt(LocalDateTime.now());
            resetPasswordToken.get().setExpiredAt(LocalDateTime.now().plusMinutes(10));
            doReturn(resetPasswordToken).when(resetPasswordServiceMock).getResetPasswordToken(confirmTokenRequest.getToken());

            assertEquals("Confirmed", userServiceMock.confirmResetPasswordToken(confirmTokenRequest));
        }

        @Test
        void testResetForgotPassword() {
            ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
            resetPasswordRequest.setEmail("mrjesus@gmail.com");
            resetPasswordRequest.setNewPassword("Honolulu94@");

            Optional<User> forgottenUser = Optional.of(new User());
            doReturn(forgottenUser).when(userRepositoryMock).findByEmailAddressIgnoreCase(resetPasswordRequest.getEmail());

            assertEquals("Your password has been changed successfully", userServiceMock.resetPassword(resetPasswordRequest));
        }
    }

