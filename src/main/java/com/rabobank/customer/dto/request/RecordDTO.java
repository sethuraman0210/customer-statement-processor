package com.rabobank.customer.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * RecordDTO holds the request data for processing the customer statement.
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"accountNumber", "description", "startBalance", "mutation", "endBalance"})
public class RecordDTO {

    /**
     * Field holds the reference id of the statement
     * Its a unique field
     */
    @JsonProperty("Reference")
    @NotNull
    private BigDecimal reference;

    /**
     * Field holds the account Number id of the customer
     */
    @JsonProperty("AccountNumber")
    @NotNull
    private String accountNumber;

    /**
     * Field holds the description id of the record
     */
    @JsonProperty("Description")
    private String description;

    /**
     * Field holds the start balance of the record
     */
    @JsonProperty("Start Balance")
    @NotNull
    private BigDecimal startBalance;

    /**
     * Field holds the mutation of the record
     */
    @JsonProperty("Mutation")
    @NotNull
    private BigDecimal mutation;

    /**
     * Field holds the end balance of the record
     */
    @JsonProperty("End Balance")
    @NotNull
    private BigDecimal endBalance;
}
