package africa.semicolon.unicoin.registration;

import africa.semicolon.unicoin.Utils.ApiResponse;
import africa.semicolon.unicoin.registration.dtos.RegistrationRequest;
import africa.semicolon.unicoin.registration.dtos.ResendTokenRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest, HttpServletRequest httpServletRequest) throws MessagingException {

        String createdUser = registrationService.register(registrationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(createdUser)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendToken(@RequestBody ResendTokenRequest tokenRequest, HttpServletRequest httpServletRequest) throws MessagingException {
        var resendTokenResponse = registrationService.resendToken(tokenRequest.getEmail());

        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(resendTokenResponse)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
