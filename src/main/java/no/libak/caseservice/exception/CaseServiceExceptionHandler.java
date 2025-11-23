package no.libak.caseservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CaseServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(CaseServiceExceptionHandler.class);

    @ExceptionHandler(CaseServiceSystemException.class)
    public ErrorResponse handleCaseServiceSystemException(CaseServiceSystemException caseServiceSystemException) {
        log.error("A system error occured: {}", caseServiceSystemException.getMessage(), caseServiceSystemException);

        return ErrorResponse.builder(caseServiceSystemException, HttpStatus.INTERNAL_SERVER_ERROR, caseServiceSystemException.getMessage())
                .title("System Error")
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException notFoundException) {
        log.warn("A resource was not found: {}", notFoundException.getMessage());

        return ErrorResponse.builder(notFoundException, HttpStatus.NOT_FOUND, notFoundException.getMessage())
                .title("Not Found")
                .build();
    }

    @ExceptionHandler(InvalidInputException.class)
    public ErrorResponse handleInvalidInputException(InvalidInputException invalidInputException) {
        log.warn("Invalid input: {}", invalidInputException.getMessage());

        return ErrorResponse.builder(invalidInputException, HttpStatus.BAD_REQUEST, invalidInputException.getMessage())
                .title("Bad Request")
                .build();
    }
}
