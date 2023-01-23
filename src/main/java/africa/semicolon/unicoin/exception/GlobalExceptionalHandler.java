package africa.semicolon.unicoin.exception;


import africa.semicolon.unicoin.Utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionalHandler {
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<?> handleRegistrationException(RegistrationException ex, WebRequest request){
        ApiErrorDetails apiErrorDetails = new ApiErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(apiErrorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ApiResponse> UserUnableToLogin(RegistrationException registrationException, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(registrationException.getMessage())
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.CONFLICT)
                .isSuccessful(false)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GenericHandler.class)
    public ResponseEntity<ApiResponse> GenericHandler(RegistrationException registrationException, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(registrationException.getMessage())
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.BAD_REQUEST)
                .isSuccessful(false)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
