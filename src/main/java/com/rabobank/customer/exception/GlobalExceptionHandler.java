package com.rabobank.customer.exception;

import com.rabobank.customer.dto.response.ValidationResponseDTO;
import com.rabobank.customer.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * GlobalExceptionHandler for the common exception handling for the application
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Method handles all the unhandled exceptions
     *
     * @param exception
     * @return ValidationResponseDTO
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<ValidationResponseDTO> handleException(Exception exception) {
        log.debug("Exception Handling for all unhandled Exception");
        return new ResponseEntity<>(new ValidationResponseDTO(Constants.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
