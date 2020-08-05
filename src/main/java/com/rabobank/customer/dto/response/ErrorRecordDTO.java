package com.rabobank.customer.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


/**
 * ErrorRecordDTO holds the response data of the error records
 * captured while processing the customer statement.
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ErrorRecordDTO {

    /**
     * Field holds the reference value of the error record
     */
    private BigDecimal reference;

    /**
     * Field holds the account Number of the error record
     */
    private String accountNumber;
}
