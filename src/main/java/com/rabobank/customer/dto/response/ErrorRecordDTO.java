package com.rabobank.customer.dto.response;

import lombok.*;

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
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public final class ErrorRecordDTO {

    /**
     * Field holds the reference value of the error record
     */
    private BigDecimal reference;

    /**
     * Field holds the account Number of the error record
     */
    private String accountNumber;
}
