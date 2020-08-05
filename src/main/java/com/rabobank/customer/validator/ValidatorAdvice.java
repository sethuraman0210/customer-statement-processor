package com.rabobank.customer.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * ValidatorAdvice initiates the bind the WebDataBinder & RequestValidator
 * <p>
 * * @author Sethuraman Sathiyamoorthy
 * * @version 1.0
 * * @since 1.0
 */

@ControllerAdvice
public class ValidatorAdvice {

    @Autowired
    protected LocalValidatorFactoryBean validator;


    /**
     * Binding method between WebDataBinder & RequestValidator
     *
     * @param binder web data binder.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new RequestValidator(validator));
    }
}