package africa.semicolon.unicoin.user;

import africa.semicolon.unicoin.exception.RegistrationException;
import africa.semicolon.unicoin.mockUtils.*;
import africa.semicolon.unicoin.registration.dtos.*;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordToken;
import africa.semicolon.unicoin.registration.resetToken.ResetPasswordTokenService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static africa.semicolon.unicoin.mockUtils.MockUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class UserServiceImplTest {
    private static final ResetPasswordTokenService resetPasswordTokenServiceMock = spy(MockUtils.ResetPasswordServiceMock());

    private final UserServiceImpl userService = new UserServiceImpl(
            userRepositoryMock,
            confirmationTokenMock(),
            resetPasswordTokenServiceMock,
            emailSenderMock,
            passwordEncoderMock
    );

    @Test
    void forgotPassword() throws MessagingException {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail("test@testing.com");

        Optional<User> user = Optional.of(new User());
        doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase(forgotPasswordRequest.getEmail());
        assertEquals(userService.forgotPassword(forgotPasswordRequest),"We have sent a reset password link to your email. Please check.");
    }

    @Test
    void confirmResetPasswordToken() {
        ConfirmTokenRequest tokenRequest = new ConfirmTokenRequest();
        tokenRequest.setEmail("test@testing.com");
        tokenRequest.setToken("85656674-1488-4d64-aca6-e78ff6d757fc");

        Optional<ResetPasswordToken> confirmationToken = Optional.of(new ResetPasswordToken());
        confirmationToken.get().setToken(tokenRequest.getToken());
        confirmationToken.get().setCreatedAt(LocalDateTime.now());
        confirmationToken.get().setExpiredAt(LocalDateTime.now().plusMinutes(10));
        doReturn(confirmationToken).when(resetPasswordTokenRepositoryMock).findByToken(tokenRequest.getToken());
        assertEquals(userService.confirmResetPasswordToken(tokenRequest), "Confirmed");
    }

    @Test
    void resetPassword() {
        ResetPasswordRequest passwordRequest = new ResetPasswordRequest();
        passwordRequest.setEmail("test@testing.com");
        passwordRequest.setNewPassword("12345");

        Optional<User> user = Optional.of(new User());
        doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase(passwordRequest.getEmail());
        assertEquals("Your password has been changed successfully", userService.resetPassword(passwordRequest));
    }

    @Test
    void getUserByEmailAddress() {
        Optional<User> user = Optional.of(new User());
        doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase("test@testing.com");
        assertEquals(user.get(), userService.getUserByEmailAddress("test@testing.com"));
    }

    @Test
    void getUserByEmailAddressThrowsAnExceptionIfEmailDoesNotExist() {
        Optional<User> user = Optional.of(new User());
        doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase("test@testing.com");
        assertThrows(RegistrationException.class, ()-> userService.getUserByEmailAddress("test@testing.com.ng"));
    }

    @Test
    void login() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@testing.com");
        request.setPassword("12345");
        Optional<User> user = Optional.of(new User());
        user.get().setIsDisabled(false);
        user.get().setPassword("12345");
        doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase(request.getEmail());
        assertEquals(userService.login(request),"Login successful");
    }

    @Test
    void deleteAccountByEmail() {
        PasswordRequest passwordRequest = new PasswordRequest();
        passwordRequest.setPassword("12345");
        String email = "test@testing.com";
        Optional<User> user = Optional.of(new User());
        user.get().setPassword("12345");
        doReturn(user).when(userRepositoryMock).findByEmailAddressIgnoreCase(email);
        assertEquals(userService.deleteAccountByEmail(email, passwordRequest),"Account delete successfully");
    }
}