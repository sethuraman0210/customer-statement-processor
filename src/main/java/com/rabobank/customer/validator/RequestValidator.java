package com.rabobank.customer.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collection;

/**
 * Spring {@link Validator} that iterates over the elements of a
 * {@link Collection} and run the validation process for each of them
 * individually.
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@Component
public class RequestValidator implements Validator {

    private final Validator validator;

    public RequestValidator(LocalValidatorFactoryBean validatorFactory) {
        this.validator = validatorFactory;
    }

    /**
     * @param clazz
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * Validate each element inside the supplied {@link Collection}.
     * <p>
     * The supplied errors instance is used to report the validation errors.
     *
     * @param target the collection that is to be validated
     * @param errors contextual state about the validation process
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void validate(Object target, Errors errors) {
        Collection collection = (Collection) target;
        for (Object object : collection) {
            ValidationUtils.invokeValidator(validator, object, errors);
        }
    }
}